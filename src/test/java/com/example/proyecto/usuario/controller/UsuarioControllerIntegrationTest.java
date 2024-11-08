package com.example.proyecto.usuario.controller;


import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.carrera.infrastructure.CarreraRepository;
import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UsuarioControllerIntegrationTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario usuario;

    @Autowired
    private CarreraRepository carreraRepository;

    public Carrera carrera;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        //usuario
        usuario = new Usuario();
        usuario.setNombre("michis adeses");
        usuario.setEmail("roger.zavaleta@utec.edu.pe");
        usuario.setPassword("12345678");
        LocalDateTime temp_time = LocalDateTime.parse("2024-09-18T07:19:34");
        usuario.setFechaRegistro(temp_time);

        //carrera
        carrera = new Carrera();
        carrera.setNombre("Ciencia de la Computacion");
    }

    @Test
    public void testSaveUsuarioAndExpectCreated() throws Exception {
        mockMvc.perform(post("/usuarios")
                        .content(objectMapper.writeValueAsString(usuario))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        Usuario updatedUsuario = usuarioRepository.findAll().get(0);
        Assertions.assertEquals(usuario.getEmail(),updatedUsuario.getEmail());
        Assertions.assertEquals(usuario.getNombre(),updatedUsuario.getNombre());
    }

}
