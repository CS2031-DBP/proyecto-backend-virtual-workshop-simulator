package com.example.proyecto.comentario.domail;

import com.example.proyecto.post.domail.Post;
import com.example.proyecto.usuario.domail.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max =250)
    private String contenido;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    @PrePersist
    public void prePersist(){
        fecha = LocalDate.now();
    }
    
}
