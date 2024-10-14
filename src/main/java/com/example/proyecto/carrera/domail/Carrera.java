package com.example.proyecto.carrera.domail;

import com.example.proyecto.curso.domail.Curso;
import com.example.proyecto.usuario.domail.Usuario;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


}
