package com.example.proyecto.material.dto;

import com.example.proyecto.material.domail.Tipo;
import lombok.Data;

@Data
public class MaterialRequestDto {

    private String nombre;

    private String url;

    private Tipo tipo;

    private Long usuarioId;

    private Long cursoId;

}
