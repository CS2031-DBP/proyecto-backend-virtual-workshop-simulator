package com.example.proyecto.material.domail;

import com.example.proyecto.curso.domail.Curso;
import com.example.proyecto.post.domail.Post;
import com.example.proyecto.usuario.domail.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(unique = true, nullable = false)
    private String nombre;

    private String urlArchivo;;

    private LocalDate fechaCreada;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Min(value = 1, message = "Minimo 1")
    @Max(value = 5, message = "Maximo de 5")
    private Double rating;

    private int numeroCalificaciones;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    @JsonBackReference
    private Curso curso;

    @PrePersist
    public void prePersist(){
        fechaCreada = LocalDate.now();
    }

    public void agregarCalificacion(double nuevaCalificacion) {
        this.rating = (this.rating * this.numeroCalificaciones + nuevaCalificacion) / (this.numeroCalificaciones + 1);
        this.numeroCalificaciones++;
    }



}
