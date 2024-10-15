package com.example.proyecto.material.dto;

import com.example.proyecto.material.domail.Material;
import com.example.proyecto.material.domail.Tipo;
import lombok.Data;

@Data
public class MaterialResponseDto {

    private Long id;

    private String nombre;

    private String urlArchivo;

    private Tipo tipo;

    private double rating;

    private String cursoNombre;

    private String usuarioNombre;

    private int numeroCalificaciones;


}
