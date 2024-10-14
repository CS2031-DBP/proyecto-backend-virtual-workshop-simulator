package com.example.proyecto.curso.domain;

import com.example.proyecto.curso.domail.Curso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CursoTest {

    public Curso curso;

    public void setUpCurso(){
        curso = new Curso();
        curso.setNombre("Ecuaciones Diferenciales");
    }
    @BeforeEach
    void setUp(){
        setUpCurso();
    }
    @Test
    void testCursoCreation(){
        assertNotNull(curso);
        assertEquals("Ecuaciones Diferenciales", curso.getNombre());
    }
    @Test
    void testCursoNull(){
        curso = null;
        assertNull(curso);
    }
}
