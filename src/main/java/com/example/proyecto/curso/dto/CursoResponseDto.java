package com.example.proyecto.curso.dto;

import com.example.proyecto.material.dto.MaterialResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class CursoResponseDto {

    private String nombre;
    private String carreraNombre;
    private List<Long> materialIds;
    private List<MaterialResponseDto> materials;

}
