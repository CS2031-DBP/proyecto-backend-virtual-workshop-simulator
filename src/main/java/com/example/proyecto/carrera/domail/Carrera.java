package com.example.proyecto.carrera.domail;

import com.example.proyecto.curso.domail.Curso;
import com.example.proyecto.post.domail.Post;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "carrera")
    @JsonManagedReference
    private List<Curso> cursos;

    @OneToMany(mappedBy = "carrera")
    @JsonManagedReference
    private List<Post> posts;


}
