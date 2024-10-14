package com.example.proyecto.post.domain;

import com.example.proyecto.post.domail.Post;
import com.example.proyecto.usuario.domail.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest {
    public Post post;

    public void setUpPost(){
        post = new Post();
        post.setTitulo("example post test");
        LocalDate temp_date = LocalDate.parse("2024-11-02");
        post.setFechaCreacion(temp_date);
    }
    @BeforeEach
    void setUp(){
        setUpPost();
    }
    @Test
    void testPostCreation(){
        assertNotNull(post);
        assertEquals("example post test", post.getTitulo());
        assertEquals("2024-11-02", (post.getFechaCreacion().toString()));
    }
    @Test
    void testPostNull(){
        post = null;
        assertNull(post);
    }

}
