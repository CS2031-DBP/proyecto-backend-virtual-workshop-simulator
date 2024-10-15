package com.example.proyecto.actividad.domail;

import com.example.proyecto.curso.domail.Curso;
import com.example.proyecto.post.domail.Post;
import com.example.proyecto.usuario.domail.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nombre;

    private Categoria tipo;

    private String enlace;

    private LocalDateTime fecha;

    private LocalDateTime fechaActividad;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    @JsonBackReference
    private Curso curso;

    @PrePersist
    public void prePersist(){
        fecha  = LocalDateTime.now();
    }


}
