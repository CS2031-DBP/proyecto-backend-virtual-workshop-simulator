package com.example.proyecto.usuario.repository;

import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import jakarta.validation.constraints.AssertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private Usuario usuario;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUpUsuarioContent() {
        usuario = new Usuario();
        usuario.setNombre("michis adeses");
        usuario.setEmail("roger.zavaleta@utec.edu.pe");
        usuario.setPassword("12345678");
        LocalDateTime temp_time = LocalDateTime.parse("2024-09-18T07:19:34");
        usuario.setFechaRegistro(temp_time);
        entityManager.persist(usuario);
    }

    @Test
    public void testAddAndGetUsuarioEmail() {
        String test_mail = "roger.zavaleta@utec.edu.pe";

        usuarioRepository.save(usuario);

        entityManager.flush();
        entityManager.clear();

        Usuario retrieveUsuario = usuarioRepository.findById(usuario.getId()).orElse(null);
        assertNotNull(retrieveUsuario);

        assertFalse(retrieveUsuario.getEmail().isEmpty());
        String mail_to_retrieve = retrieveUsuario.getEmail();
        assertEquals(test_mail, mail_to_retrieve);
    }

    @Test
    public void testFindAllByNameAndId() {
        String test_mail = "roger.zavaleta@utec.edu.pe";
        Optional<Usuario> Usuarios = usuarioRepository.findByEmail(test_mail);

        assertFalse(Usuarios.isEmpty());

        Usuario retrievedUsuario = Usuarios.get();
        assertEquals(usuario, retrievedUsuario);
    }

    @Test
    public void testDeleteUsuario() {
        usuarioRepository.delete(usuario);
        Usuario DeletedUsuario = usuarioRepository.findById(usuario.getId()).orElse(null);
        assertNull(DeletedUsuario);
        assertFalse(usuarioRepository.existsById(usuario.getId()));
    }
}
