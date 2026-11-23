package cl.duoc.pasteleria.service;

import cl.duoc.pasteleria.dto.*;
import cl.duoc.pasteleria.exception.ResourceNotFoundException;
import cl.duoc.pasteleria.model.Usuario;
import cl.duoc.pasteleria.repository.UsuarioRepository;
import cl.duoc.pasteleria.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreoIgnoreCase(loginRequest.getCorreo());
        
        if (usuario.isEmpty()) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        Usuario user = usuario.get();
        
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Correo o contraseña incorrectos");
        }

        String token = jwtTokenProvider.generateTokenFromUsername(user.getCorreo());

        return LoginResponse.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .correo(user.getCorreo())
                .rol(user.getRol())
                .token(token)
                .build();
    }

    public LoginResponse register(RegistroRequest registroRequest) {
        if (usuarioRepository.findByCorreoIgnoreCase(registroRequest.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Usuario usuario = Usuario.builder()
                .nombre(registroRequest.getNombre())
                .correo(registroRequest.getCorreo())
                .password(passwordEncoder.encode(registroRequest.getPassword()))
                .telefono(registroRequest.getTelefono())
                .fechaNacimiento(registroRequest.getFechaNacimiento())
                .region(registroRequest.getRegion())
                .comuna(registroRequest.getComuna())
                .direccion(registroRequest.getDireccion())
                .rol(registroRequest.getRol() != null ? registroRequest.getRol() : cl.duoc.pasteleria.model.Rol.CLIENTE)
                .estado("ACTIVO")
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        String token = jwtTokenProvider.generateTokenFromUsername(usuarioGuardado.getCorreo());

        return LoginResponse.builder()
                .id(usuarioGuardado.getId())
                .nombre(usuarioGuardado.getNombre())
                .correo(usuarioGuardado.getCorreo())
                .rol(usuarioGuardado.getRol())
                .token(token)
                .build();
    }

    public UsuarioDTO obtenerPerfil(String correo) {
        Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .telefono(usuario.getTelefono())
                .fechaNacimiento(usuario.getFechaNacimiento())
                .region(usuario.getRegion())
                .comuna(usuario.getComuna())
                .direccion(usuario.getDireccion())
                .fechaEntregaPreferida(usuario.getFechaEntregaPreferida())
                .rol(usuario.getRol())
                .estado(usuario.getEstado())
                .build();
    }
}
