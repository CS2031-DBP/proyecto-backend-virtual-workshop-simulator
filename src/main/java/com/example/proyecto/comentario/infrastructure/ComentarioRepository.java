package com.example.proyecto.comentario.infrastructure;

import com.example.proyecto.comentario.domail.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
