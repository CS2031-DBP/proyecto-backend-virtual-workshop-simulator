package com.example.proyecto.calificacion.dto;

import lombok.Data;

@Data
public class CalificacionRequestDto {
    private int  valor;
    private Long usuarioId;
    private Long materialId;
}

