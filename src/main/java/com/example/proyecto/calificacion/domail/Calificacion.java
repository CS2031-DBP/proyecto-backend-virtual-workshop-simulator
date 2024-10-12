package com.example.proyecto.calificacion.domail;

import com.example.proyecto.material.domail.Material;
import com.example.proyecto.usuario.domail.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(1)
    @Max(5)
    private int valor;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

}

