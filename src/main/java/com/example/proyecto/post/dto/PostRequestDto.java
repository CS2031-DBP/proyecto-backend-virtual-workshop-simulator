package com.example.proyecto.post.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PostRequestDto {

    private  Long id;

    private Long usuarioId;

    private Long carreraId;

    private String titulo;

    private String contenido;

}
