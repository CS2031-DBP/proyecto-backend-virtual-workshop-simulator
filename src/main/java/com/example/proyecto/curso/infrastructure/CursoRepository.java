package com.example.proyecto.curso.infrastructure;

import com.example.proyecto.curso.domail.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
