package com.example.proyecto.post.dto;

import com.example.proyecto.material.dto.MaterialResponseDto;
import com.example.proyecto.usuario.dto.UsuarioRequestDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDto {

    private Long postId;

    private String titulo;

    private LocalDateTime fechaCreacion;

    private String autorNombre;

    private List<MaterialResponseDto> materiales;

    private List<Long> comentariosIds;

    private List<Long> actividadesIds;
}
