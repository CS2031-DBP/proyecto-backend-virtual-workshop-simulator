package com.example.proyecto.post.infrastructure;

import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.post.domail.Post;
import com.example.proyecto.usuario.domail.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByCarrera(Carrera carrera, Pageable pageable);
    Page<Post> findByAutor(Usuario usuario, Pageable pageable);

}

