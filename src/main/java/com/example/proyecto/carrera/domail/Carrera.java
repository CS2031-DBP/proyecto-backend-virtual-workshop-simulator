package com.example.proyecto.carrera.domail;

import com.example.proyecto.curso.domail.Curso;
import com.example.proyecto.usuario.domail.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
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


}
