package com.example.proyecto.post.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PostRequestDto {

    private String titulo;

    private Long usuarioId;

    private List<Long> materialesIds;

    private List<Long> actividadesIds;
}
