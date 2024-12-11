package app.VilaExplorer.controller;

import app.VilaExplorer.domain.Rol;
import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.domain.UsuarioRol;
import app.VilaExplorer.payload.request.LoginRequest;
import app.VilaExplorer.payload.request.SignUpRequest;
import app.VilaExplorer.payload.response.JwtResponse;
import app.VilaExplorer.payload.response.MessageResponse;
import app.VilaExplorer.repository.RolRepository;
import app.VilaExplorer.repository.UsuarioRepository;
import app.VilaExplorer.repository.UsuarioRolRepository;
import app.VilaExplorer.security.jwt.JwtUtils;
import app.VilaExplorer.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    UsuarioRolRepository usuarioRolRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse
                        (
                                jwt,
                                userDetails.getId(),
                                userDetails.getUsername(),
                                userDetails.getEmail(),
                                userDetails.getPassword(),
                                roles
                        )
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, @RequestParam(value = "rol") String rol) {
        if (userRepository.existsByNombre(signUpRequest.getNombre())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        if (rolRepository.findByNombre(rol).isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Role " + rol + " is not found in database"));
        }

        // Create new user's account
        Usuario user = new Usuario(
                signUpRequest.getNombre(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                LocalDateTime.now(),
                true
        );


        Usuario usuarioGuardado = userRepository.save(user);
        Rol rolFromDB = rolRepository.findByNombre(rol).get();

        // Asigna el rol después de guardar el usuario
        usuarioGuardado.setRolActual(rolFromDB);

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(usuarioGuardado);
        usuarioRol.setRol(rolFromDB);
        usuarioRol.setFechaDeAsignacion(LocalDateTime.now());

        usuarioRolRepository.save(usuarioRol);

        usuarioGuardado.getRoles().add(usuarioRol);

        userRepository.save(usuarioGuardado);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    // Metodo para registrar usuarios moviles (clientes)
    @PostMapping("/signup-mobile")
    public ResponseEntity<?> registerMobileUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        // Validacion de existencia de nombre y correo
        if (userRepository.existsByNombre(signUpRequest.getNombre())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Crear cuenta de usuario con rol Cliente
        Usuario user = new Usuario(
                signUpRequest.getNombre(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                LocalDateTime.now(),
                true
        );

        Usuario savedUser = userRepository.save(user);

        // Asignar rol de Cliente
        Rol clienteRol = rolRepository.findByNombre("Cliente").orElseThrow(() ->
                new RuntimeException("Error: Role Cliente not found"));
        UsuarioRol usuarioRol = new UsuarioRol(savedUser, clienteRol, LocalDateTime.now());
        usuarioRolRepository.save(usuarioRol);

        savedUser.getRoles().add(usuarioRol);
        savedUser.setRolActual(clienteRol);

        userRepository.save(savedUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully as Cliente!"));
    }

    // Metodo para registrar usuarios administrativos (Administrador o Redactor)
    @PostMapping("/signup-admin")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<?> registerAdminUser(@Valid @RequestBody SignUpRequest signUpRequest,
                                               @RequestParam(value = "rol") String rol) {
        // Validacion de existencia de nombre y correo
        if (userRepository.existsByNombre(signUpRequest.getNombre())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Validar que el rol proporcionado exista
        Rol rolFromDB = rolRepository.findByNombre(rol).orElseThrow(() ->
                new RuntimeException("Error: Role " + rol + " not found in database"));

        // Crear cuenta de usuario con rol especificado
        Usuario user = new Usuario(
                signUpRequest.getNombre(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                LocalDateTime.now(),
                true
        );

        Usuario savedUser = userRepository.save(user);

        // Asignar el rol proporcionado
        UsuarioRol usuarioRol = new UsuarioRol(savedUser, rolFromDB, LocalDateTime.now());
        usuarioRolRepository.save(usuarioRol);

        savedUser.getRoles().add(usuarioRol);
        savedUser.setRolActual(rolFromDB);

        userRepository.save(savedUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully with role " + rol + "!"));
    }








}
