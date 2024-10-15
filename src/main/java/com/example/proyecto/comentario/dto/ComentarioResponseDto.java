package com.example.proyecto.comentario.dto;

import com.example.proyecto.usuario.dto.UsuarioResponseDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComentarioResponseDto {

    private Long id;

    private String contenido;

    private LocalDateTime fecha;

    private String usuarioNombre;

}
