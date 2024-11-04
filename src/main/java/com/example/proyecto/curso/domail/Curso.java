package com.example.proyecto.curso.domail;

import com.example.proyecto.actividad.domail.Actividad;
import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.material.domail.Material;
import com.example.proyecto.post.domail.Post;
import com.example.proyecto.usuario.domail.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(unique = true, nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "carrera_id")
    @JsonBackReference
    private Carrera carrera;

    @OneToMany(mappedBy = "curso")
    @JsonManagedReference
    private List<Material> materiales;

    @OneToMany(mappedBy = "curso")
    @JsonManagedReference
    private List<Actividad> actividades;

    @OneToMany(mappedBy = "curso")
    private List<Post> posts;



}
