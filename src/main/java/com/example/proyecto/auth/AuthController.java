package com.example.proyecto.auth;

import org.springframework.http.HttpStatus;

import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.dto.UsuarioRequestDto;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UsuarioRepository usuarioRepository,
                          PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UsuarioRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Usuario autenticado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Autenticación fallida");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UsuarioRequestDto signUpRequest) {
        if (usuarioRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: El email ya está en uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(signUpRequest.getNombre());
        usuario.setEmail(signUpRequest.getEmail());
        usuario.setPassword(encoder.encode(signUpRequest.getPassword()));

        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuario registrado exitosamente");
    }
}

