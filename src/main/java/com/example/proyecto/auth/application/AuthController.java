package com.example.proyecto.auth.application;

import com.example.proyecto.auth.domain.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.proyecto.auth.dto.JwtTokenToUse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.proyecto.auth.dto.DatosNecesariosIniciarSesion;
import com.example.proyecto.auth.dto.DatosNecesariosRegistro;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenToUse> inicio_sesion(@RequestBody DatosNecesariosIniciarSesion datosNecesariosIniciarSesion) {
        return ResponseEntity.ok(authService.realizar_inicio_sesion(datosNecesariosIniciarSesion));
    }
    @PostMapping("/register")
    public ResponseEntity<JwtTokenToUse> registrar_usuario(@RequestBody DatosNecesariosRegistro datosNecesariosRegistro) {
        return ResponseEntity.ok(authService.realizar_registro(datosNecesariosRegistro));
    }
}
