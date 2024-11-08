package com.example.proyecto.auth.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DatosNecesariosRegistro {
    private Boolean isAdminr=false;
    private String nombre;
    private String email;
    private String password;
    private LocalDateTime fechaRegistro;
}
