package com.example.proyecto.auth.application;

import com.example.proyecto.auth.domain.AuthService;
import com.example.proyecto.auth.dto.DatosNecesariosIniciarSesion;
import com.example.proyecto.auth.dto.DatosNecesariosRegistro;
import com.example.proyecto.auth.dto.JwtTokenToUse;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenToUse> inicio_sesion(@RequestBody DatosNecesariosIniciarSesion datosNecesariosIniciarSesion) {
        return ResponseEntity.ok(authService.realizar_inicio_sesion(datosNecesariosIniciarSesion));
    }
    @PostMapping("/register")
    public ResponseEntity<JwtTokenToUse> registrar_usuario(@RequestBody DatosNecesariosRegistro datosNecesariosRegistro) {
        return ResponseEntity.ok(authService.realizar_registro(datosNecesariosRegistro));
    }

    @GetMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        boolean exists = usuarioRepository.existsByEmail(email);
        System.out.println(exists);
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @GetMapping("/checkNombre")
    public ResponseEntity<?> checkNombre(@RequestParam String nombre) {
        boolean exists = usuarioRepository.existsByNombre(nombre);
        System.out.println(exists);
        return ResponseEntity.ok(Map.of("exists", exists));
    }

}
