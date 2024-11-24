package com.example.proyecto.curso.infrastructure;

import com.example.proyecto.curso.domail.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Optional<Curso> findByNombre(String nombre);
    interface UserProjection {
        String getNombre();
    }

}
