package com.example.proyecto.actividad.infrastructure;

import com.example.proyecto.actividad.domail.Actividad;
import com.example.proyecto.curso.domail.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {

   }
