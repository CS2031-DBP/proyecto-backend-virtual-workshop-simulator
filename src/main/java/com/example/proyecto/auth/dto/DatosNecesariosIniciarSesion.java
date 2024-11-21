package com.example.proyecto.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class DatosNecesariosIniciarSesion {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
