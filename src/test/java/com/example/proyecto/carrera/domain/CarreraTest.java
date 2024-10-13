package com.example.proyecto.carrera.domain;

import com.example.proyecto.carrera.domail.Carrera;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarreraTest {

    public Carrera carrera;

    public void setUpCarrera(){
        carrera = new Carrera();
        carrera.setNombre("Ciencia de la Computacion");
    }

    @BeforeEach
    void setUp(){
        setUpCarrera();
    }
    @Test
    void testCarreraCreation(){
        assertNotNull(carrera);
        assertEquals("Ciencia de la Computacion", carrera.getNombre());}

    @Test
    void testCarreraNull(){
        carrera = null;
        assertNull(carrera);
    }
}
