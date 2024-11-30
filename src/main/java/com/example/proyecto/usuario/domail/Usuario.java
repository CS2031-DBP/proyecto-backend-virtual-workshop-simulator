package com.example.proyecto.usuario.domail;

import com.example.proyecto.actividad.domail.Actividad;
import com.example.proyecto.calificacion.domail.Calificacion;
import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.comentario.domail.Comentario;
import com.example.proyecto.material.domail.Material;
import com.example.proyecto.post.domail.Post;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @JoinColumn(nullable = false, unique = true)
    private String nombre;

    @NotNull
    @JoinColumn(nullable = false, unique = true)
    private String lastName;



    @Email
    @JoinColumn(nullable = false, unique = true)
    private String email;

    @Size(min = 8, message = "la contraseña debe tener un min de 8 caracteres")
    private String password;

    private String perfilUrl;

    private LocalDateTime fechaRegistro;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(
            name = "usuario_carrera",
            joinColumns = @JoinColumn(
                    name = "usuario_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "carrera_id",
                    referencedColumnName = "id")
    )
    private List<Carrera> carreras;

    @OneToMany(mappedBy = "autor")
    private List<Post> posts;

    @OneToMany(mappedBy = "propietario")
    private List<Material> materiales;

    @OneToMany(mappedBy = "usuario")
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference
    private List<Calificacion> calificaciones;

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference
    private List<Actividad> actividades;

    @PrePersist
    public void onPrePersist() {
        fechaRegistro = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }






}