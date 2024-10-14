package com.example.proyecto.actividad.dto;

import com.example.proyecto.actividad.domail.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ActividadRequestDto {

    private String nombre;

    private Categoria tipo;

    private LocalDate fechaActividad;

    private LocalTime hora;



}
