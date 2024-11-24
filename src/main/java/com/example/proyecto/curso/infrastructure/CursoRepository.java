package com.example.proyecto.curso.infrastructure;

import com.example.proyecto.curso.domail.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Optional<Curso> findByNombre(String nombre);

    @Query("SELECT c FROM Curso c WHERE c.carrera.id = :carreraId")
    List<Curso> findByCarreraId(@Param("carreraId") Long carreraId);

}
