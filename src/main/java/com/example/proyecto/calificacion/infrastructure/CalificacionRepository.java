package com.example.proyecto.calificacion.infrastructure;

import com.example.proyecto.calificacion.domail.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    Optional<Calificacion> findByUsuarioIdAndMaterialId(Long usuarioId, Long materialId);

    @Query("SELECT AVG(c.valor) FROM Calificacion c WHERE c.material.id = :materialId")
    double promedioCalificacionPorMaterial(@Param("materialId") Long materialId);
}
