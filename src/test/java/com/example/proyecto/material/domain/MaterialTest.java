package com.example.proyecto.material.domain;

import com.example.proyecto.material.domail.Material;
import com.example.proyecto.material.domail.Tipo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MaterialTest {

    public Material material;
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

    @BeforeEach
    void setUp(){
        setUpMaterial();
    }
    @Test
    void testMaterialCreation(){
        assertNotNull(material);
        assertEquals("Vdeo Asesoria 3 EDO", material.getNombre());
        assertEquals("https://asesoria.com/vdeo1", material.getUrl());
        assertEquals(3.77, material.getRating());
        assertEquals(Tipo.VIDEO, material.getTipo());
        assertEquals("2024-02-13", (material.getFechaCreada().toString()));
        assertEquals(4, material.getNumeroCalificaciones());
    }
    @Test
    void testMaterialNull(){
        material = null;
        assertNull(material);
    }
}
