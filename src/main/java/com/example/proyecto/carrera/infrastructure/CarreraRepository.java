package com.example.proyecto.carrera.infrastructure;

import com.example.proyecto.carrera.domail.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {

    Optional<Carrera> findByNombre(String nombre);
}
