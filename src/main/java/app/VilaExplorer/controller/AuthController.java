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

//https://github.com/bezkoder/spring-boot-spring-security-jwt-authentication
//http://localhost:8080/api/auth/signup?username=Pepe&email=pepe@gmail.com&password=1234&role=admin
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
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
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        System.out.println("esta autenticado??  " + authentication.isAuthenticated());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        List<String> roles1 = jwtUtils.getRolesFromJwtToken(jwt); // Este método debes implementarlo si no lo tienes
        System.out.println("Roles: " + roles1);

        System.out.println("CLAVE JWT PARA USUARIO " + jwt);

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
}
