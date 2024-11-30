package com.example.proyecto.auth.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DatosNecesariosRegistro {
    private String nombre;
    private String lastName;
    private String email;
    private String password;
}
