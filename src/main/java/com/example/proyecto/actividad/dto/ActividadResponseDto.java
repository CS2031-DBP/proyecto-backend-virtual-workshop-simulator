package com.example.proyecto.actividad.dto;

import com.example.proyecto.actividad.domail.Categoria;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ActividadResponseDto {

    private Long id;

    private String nombre;

    private String nameUsuario;

    private String perfilUsuario;

    private Categoria tipo;

    private String enlace;

    private LocalDateTime fecha;

    private LocalDateTime fechaActividad;

    private Long postId;

}
