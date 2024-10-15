package com.example.proyecto.usuario.infrastructure;

import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.usuario.domail.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
