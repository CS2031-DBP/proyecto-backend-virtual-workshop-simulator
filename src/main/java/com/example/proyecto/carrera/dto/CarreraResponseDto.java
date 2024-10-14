package com.example.proyecto.carrera.dto;

import lombok.Data;

import java.util.List;

@Data
public class CarreraResponseDto {

    private Long id;
    private String nombre;
    private List<Long> cursosIds;


}
