package com.example.proyecto.material.infrastructure;

import com.example.proyecto.material.domail.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    Optional<Material> findByNombre(String nombre);
}
