package com.example.proyecto.usuario.domail;

import com.example.proyecto.actividad.domail.Actividad;
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
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Service
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String nombre;

    @Email
    @JoinColumn(nullable = false, unique = true)
    private String email;

    @Size(min = 8, message = "la contraseña debe tener un min de 8 caracteres")
    private String password;

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

    private Role role;

    @OneToMany(mappedBy = "usuario")
    private List<Post> posts;

    @OneToMany(mappedBy = "usuario")
    private List<Material> materiales;

    @OneToMany(mappedBy = "usuario")
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference
    private List<Actividad> actividades;

    @PrePersist
    public void onPrePersist() {
        fechaRegistro = LocalDateTime.now();
    }
    @Transient
    private String rolePrefix = "ROLE_";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rolePrefix + role.name()));
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
