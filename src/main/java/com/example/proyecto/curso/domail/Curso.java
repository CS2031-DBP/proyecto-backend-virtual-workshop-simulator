package com.example.proyecto.curso.domail;

import com.example.proyecto.actividad.domail.Actividad;
import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.material.domail.Material;
import com.example.proyecto.post.domail.Post;
import com.example.proyecto.usuario.domail.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long curso_id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;

    @OneToMany(mappedBy = "curso")
    private List<Material> materiales;

    @OneToMany(mappedBy = "curso")
    private List<Actividad> actividades;

    @OneToMany(mappedBy = "curso")
    private List<Post> posts;



}
