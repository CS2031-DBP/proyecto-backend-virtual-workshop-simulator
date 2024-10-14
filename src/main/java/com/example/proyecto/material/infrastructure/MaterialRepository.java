package com.example.proyecto.material.infrastructure;

import com.example.proyecto.material.domail.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}
