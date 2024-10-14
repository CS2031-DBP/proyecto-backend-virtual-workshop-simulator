package com.example.proyecto.actividad.domain;

import com.example.proyecto.actividad.domail.Actividad;
import com.example.proyecto.actividad.domail.Categoria;
import com.example.proyecto.curso.domail.Curso;
import com.example.proyecto.usuario.domail.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ActividadTest {
    public Actividad actividad;

    public Usuario usuario;

    public Curso curso;

    public void setUpUsuario(){
        usuario = new Usuario();
        usuario.setNombre("michis adeses");
        usuario.setEmail("roger.zavaleta@utec.edu.pe");
        usuario.setPassword("12345678");
        LocalDateTime temp_time = LocalDateTime.parse("2024-09-18T07:19:34");
        usuario.setFechaRegistro(temp_time);
    }

    public void setUpCurso(){
        curso = new Curso();
        curso.setNombre("Ecuaciones Diferenciales");
    }

    public void setUpActividad(){
        actividad = new Actividad();
        actividad.setNombre("quizz parcial");
        actividad.setTipo(Categoria.QUIZZ);
        actividad.setEnlace("https://parcial-leak-utec.com");
        LocalDateTime temp_time1 = LocalDateTime.parse("2023-06-03T16:36:18");
        actividad.setFecha(temp_time1);
        LocalDateTime temp_time2 = LocalDateTime.parse("2023-07-03T16:36:18");
        actividad.setFechaActividad(temp_time2);
    }

    @BeforeEach
    void setUp(){
        setUpUsuario();
        setUpCurso();
        setUpActividad();
    }
    @Test
    void testActividadCreation(){
        assertNotNull(actividad);
        assertEquals("quizz parcial", actividad.getNombre());
        assertEquals(Categoria.QUIZZ, actividad.getTipo());
        assertEquals("https://parcial-leak-utec.com", actividad.getEnlace());
        assertEquals("2023-06-03T16:36:18", (actividad.getFecha().toString()));
        assertEquals("2023-07-03T16:36:18", (actividad.getFechaActividad().toString()));
    }
    @Test
    void testActividadCurso(){
        //añadir Curso a Actividad
        actividad.setCurso(curso);
        //
        assertEquals(curso, actividad.getCurso());
    }

    @Test
    void testCActividadUsuario(){
        //añadir usuatio a Actividad
        actividad.setUsuario(usuario);
        //
        assertEquals(usuario, actividad.getUsuario());
    }
    @Test
    void testActividadNull(){
        actividad = null;
        assertNull(actividad);
    }
}
