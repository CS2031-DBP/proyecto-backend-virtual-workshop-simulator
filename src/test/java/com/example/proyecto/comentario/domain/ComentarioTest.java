package com.example.proyecto.comentario.domain;

import com.example.proyecto.comentario.domail.Comentario;
import com.example.proyecto.post.domail.Post;
import com.example.proyecto.usuario.domail.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ComentarioTest {

    public Comentario comentario;

    public Usuario usuario;
    private Post post;

    public void setUpUsuario(){
        usuario = new Usuario();
        usuario.setNombre("michis adeses");
        usuario.setEmail("roger.zavaleta@utec.edu.pe");
        usuario.setPassword("12345678");
        LocalDateTime temp_time = LocalDateTime.parse("2024-09-18T07:19:34");
        usuario.setFechaRegistro(temp_time);
    }
    public void setUpPost(){
        post = new Post();
        post.setTitulo("example post test");
        LocalDate temp_date = LocalDate.parse("2024-11-02");
        post.setFechaCreacion(temp_date);
    }
    public void setUpComentario(){
        comentario = new Comentario();
        comentario.setContenido("Me fue muy util esta asesoria");
        //añadir usuatio a Comentario
        comentario.setUsuario(usuario);
        //
        //añadir post a comentario
        comentario.setPost(post);
        //
        LocalDate temp_time = LocalDate.parse("2024-09-18");
        comentario.setFecha(temp_time);
    }

    @BeforeEach
    void setUp(){
        setUpUsuario();
        setUpPost();
        setUpComentario();
    }
    @Test
    void testComentarioCreation(){
        assertNotNull(usuario);
        assertEquals("michis adeses", usuario.getNombre());
        assertEquals("roger.zavaleta@utec.edu.pe", usuario.getEmail());
        assertEquals("12345678", usuario.getPassword());
        assertEquals("2024-09-18T07:19:34", (usuario.getFechaRegistro().toString()));
    }
    @Test
    void testComentarioPost(){
        assertEquals(post, comentario.getPost());
    }

    @Test
    void testComentarioUsuario(){
        assertEquals(usuario, comentario.getUsuario());
    }

    @Test
    void testDriverNull(){
        comentario = null;
        assertNull(comentario);
    }

}
