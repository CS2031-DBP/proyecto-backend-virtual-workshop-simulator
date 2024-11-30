package com.example.proyecto.post.domail;

import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.comentario.domail.Comentario;
import com.example.proyecto.usuario.domail.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String titulo;

    private String contenido;

    private LocalDateTime fechaCreacion;



    @ManyToOne
    @JoinColumn(name = "autor_id")
    @JsonBackReference
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "carrera_id")
    @JsonBackReference
    private Carrera carrera;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comentario> comentarios;


    @PrePersist
    public void prePersist(){
        fechaCreacion = LocalDateTime.now();
    }
}
