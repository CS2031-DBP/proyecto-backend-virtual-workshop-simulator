package com.example.proyecto.usuario.modail;

import com.example.proyecto.calificacion.domail.Calificacion;
import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.material.domail.Material;
import com.example.proyecto.post.domail.Post;
import com.example.proyecto.usuario.domail.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {
    public Usuario usuario;

    //inicializar valores
    public void setUpUsuario(){
        usuario = new Usuario();
        usuario.setNombre("michis adeses");
        usuario.setEmail("roger.zavaleta@utec.edu.pe");
        usuario.setPassword("12345678");
        LocalDateTime temp_time = LocalDateTime.parse("2024-09-18T07:19:34");
        usuario.setFechaRegistro(temp_time);
    }

    @BeforeEach
    void setUp(){
        setUpUsuario();
    }
    @Test
    void testUsuarioCreation(){
        assertNotNull(usuario);
        assertEquals("michis adeses", usuario.getNombre());
        assertEquals("roger.zavaleta@utec.edu.pe", usuario.getEmail());
        assertEquals("12345678", usuario.getPassword());
        assertEquals("2024-09-18T07:19:34", (usuario.getFechaRegistro().toString()));
    }
    @Test
    void testUsuarioNull(){
        usuario = null;
        assertNull(usuario);
    }



}