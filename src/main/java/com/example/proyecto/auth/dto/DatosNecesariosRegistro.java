package com.example.proyecto.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DatosNecesariosRegistro {
    @NotNull
    private String nombre;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String perfilUrl;
}
