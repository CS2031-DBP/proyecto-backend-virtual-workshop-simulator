package com.example.proyecto.calificacion.infrastructure;

import com.example.proyecto.calificacion.domail.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    Optional<Calificacion> findByUsuarioIdAndMaterialId(Long usuarioId, Long materialId);
}
