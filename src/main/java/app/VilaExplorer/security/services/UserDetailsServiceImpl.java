package app.VilaExplorer.security.services;

import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Usuario user;
        if (nombre.contains("@")) {
            user = usuarioRepository.findByEmail(nombre)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + nombre));
            return UserDetailsImpl.build(user);
        }
        user = usuarioRepository.findByNombre(nombre).orElseThrow(() -> new UsernameNotFoundException("User not Found with name: " + nombre));
        return UserDetailsImpl.build(user);
    }

    @Transactional
    public UserDetails loadUserById(Long idUsuario) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found with ID: " + idUsuario));
        return UserDetailsImpl.build(user);
    }
}