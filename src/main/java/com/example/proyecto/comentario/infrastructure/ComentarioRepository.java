package com.example.proyecto.comentario.infrastructure;

import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.comentario.domail.Comentario;
import com.example.proyecto.post.domail.Post;
import com.example.proyecto.usuario.domail.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    Page<Comentario> findByUsuario(Usuario usuario, Pageable pageable);
}
