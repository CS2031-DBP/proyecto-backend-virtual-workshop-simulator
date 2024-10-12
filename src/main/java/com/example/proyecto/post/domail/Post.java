package com.example.proyecto.post.domail;

import com.example.proyecto.actividad.domail.Actividad;
import com.example.proyecto.comentario.domail.Comentario;
import com.example.proyecto.curso.domail.Curso;
import com.example.proyecto.material.domail.Material;
import com.example.proyecto.usuario.domail.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long post_id;

    private String titulo;

    private LocalDate fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "post")
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "post")
    private List<Material> materiales;

    /*
    @OneToMany(mappedBy = "post")
    private List<Actividad> actividades;
    */

    @PrePersist
    public void prePersist(){
        fechaCreacion = LocalDate.now();
    }
}
