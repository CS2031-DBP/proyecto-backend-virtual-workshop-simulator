package com.example.proyecto.carrera.dto;

import com.example.proyecto.curso.domail.Curso;
import lombok.Data;

import java.util.List;

@Data
public class CarreraResponseDto {

    private Long id;
    private String nombre;
    private List<Curso> cursos;


}
