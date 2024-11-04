package com.example.proyecto.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class UsuarioRequestDto {

    private String nombre;

    @Email
    private String email;

    @Size(min = 8, message = "Contraseña minimo de 8 carcateres")
    private String password;

    //private Long carreraId;

}
