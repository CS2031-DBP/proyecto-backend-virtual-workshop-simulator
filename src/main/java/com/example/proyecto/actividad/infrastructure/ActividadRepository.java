package com.example.proyecto.actividad.infrastructure;

import com.example.proyecto.actividad.domail.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
}
