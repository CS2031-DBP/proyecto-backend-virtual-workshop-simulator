package com.example.proyecto.post.dto;

import com.example.proyecto.comentario.domail.Comentario;
import com.example.proyecto.material.dto.MaterialResponseDto;
import com.example.proyecto.usuario.dto.UsuarioRequestDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDto {

    private Long postId;

    private String titulo;

    private String autorNombre;

    private String contenido;

    private LocalDateTime fechaCreacion;

    private List<Comentario> comentariosIds;

}
