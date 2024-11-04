package com.example.proyecto.usuario.dto;

import com.example.proyecto.carrera.domail.Carrera;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UsuarioResponseDto {

    private Long id;

    private String nombre;

    private String email;

    private LocalDateTime fechaRegistro;

    private List<Carrera> carreras;

    private List<Long> postIds;

    private List<Long> materialesIds;

    private List<Long> comentariosIds;


}
