package com.example.proyecto.material.domail;

import com.example.proyecto.calificacion.domail.Calificacion;
import com.example.proyecto.curso.domail.Curso;
import com.example.proyecto.post.domail.Post;
import com.example.proyecto.usuario.domail.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "material")
    @JsonManagedReference
    private List<Calificacion> calificaciones;


    @PrePersist
    public void prePersist(){
        fechaCreada = LocalDate.now();
    }

    public void agregarCalificacion(double nuevaCalificacion) {
        if (this.rating == null) {
            this.rating = nuevaCalificacion;
        } else {
            this.rating = (this.rating * this.numeroCalificaciones + nuevaCalificacion) / (this.numeroCalificaciones + 1);
        }
        this.numeroCalificaciones++;
    }



}
