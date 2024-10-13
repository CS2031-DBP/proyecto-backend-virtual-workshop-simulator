package com.example.proyecto.calificacion.domain;

import com.example.proyecto.calificacion.domail.Calificacion;
import com.example.proyecto.material.domail.Material;
import com.example.proyecto.material.domail.Tipo;
import com.example.proyecto.usuario.domail.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CalificacionTest {
    public Calificacion calificacion;
    public Usuario usuario;
    public Material material;

    public void setUpUsuario(){
        usuario = new Usuario();
        usuario.setNombre("michis adeses");
        usuario.setEmail("roger.zavaleta@utec.edu.pe");
        usuario.setPassword("12345678");
        LocalDateTime temp_time = LocalDateTime.parse("2024-09-18T07:19:34");
        usuario.setFechaRegistro(temp_time);
    }
    public void setUpMaterial(){
        material = new Material();
        material.setNombre("Vdeo Asesoria 3 EDO");
        material.setUrl("https://asesoria.com/vdeo1");
        LocalDate temp_date = LocalDate.parse("2024-02-13");
        material.setFechaCreada(temp_date);
        material.setTipo(Tipo.VIDEO);
        material.setRating(3.77);
        material.setNumeroCalificaciones(4);
    }
    public void setUpCalificacion(){
        calificacion = new Calificacion();
        calificacion.setValor(3);
    }

    @BeforeEach
    void setUp(){
        setUpUsuario();
        setUpMaterial();
        setUpCalificacion();
    }
    @Test
    void testCalificacionCreation(){
        assertNotNull(calificacion);
        assertEquals(3, calificacion.getValor());

    }
    @Test
    void testCalificacionUsuario(){
        //añadir usuatio a Calificacion
        calificacion.setUsuario(usuario);
        //
        assertEquals(usuario, calificacion.getUsuario());
    }
    @Test
    void testCalificacionMaterial(){
        //añadir Material a Calificacion
        calificacion.setMaterial(material);
        //
        assertEquals(material, calificacion.getMaterial());
    }
    @Test
    void testCalificacionNull(){
        calificacion = null;
        assertNull(calificacion);
    }
}
