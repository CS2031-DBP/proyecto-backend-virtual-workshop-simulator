package com.example.proyecto.comentario.dto;

import lombok.Data;

@Data
public class ComentarioRequestDto {

    private String contenido;

    private Long usuarioId;

    private Long postId;
}
