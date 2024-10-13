package com.example.proyecto.usuario.application;

import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.carrera.infrastructure.CarreraRepository;
import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.dto.UsuarioRequestDto;
import com.example.proyecto.usuario.dto.UsuarioResponseDto;
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
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    public void testSaveUsuarioAndIscribirCarrera() throws Exception {
        carreraRepository.save(carrera);
        var res = mockMvc.perform(post("/{usuarioId}/carreras/{carreraId}/inscribir",usuario.getId(),carrera.getId())
                )
                .andExpect(status().isCreated());
        //test añadir carrera a usuario
        Usuario updatedUsuario = usuarioRepository.findAll().get(0);
        Assertions.assertEquals(usuario.getEmail(),updatedUsuario.getEmail());
        Assertions.assertEquals(carrera.getNombre(),updatedUsuario.getCarreras().get(0).getNombre());

        //test error
        Optional<Usuario> savedUsuario = usuarioRepository.findById((usuario.getId()));
        Assertions.assertTrue(savedUsuario.isPresent());

        mockMvc.perform(post("/{usuarioId}/carreras/{carreraId}/inscribir",usuario.getId(),carrera.getId())
                )
                .andExpect(status().isConflict());
    }

    @Test
    public void testGetUsuario() throws Exception {
        mockMvc.perform(get("/{usuarioId}", usuario.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteDriver() throws Exception {
        Usuario temp= new Usuario();
        temp.setNombre("michis adeses");
        temp.setEmail("roger.zavaleta@utec.edu.pe");
        temp.setPassword("12345678");
        LocalDateTime temp_time = LocalDateTime.parse("2024-09-18T07:19:34");
        temp.setFechaRegistro(temp_time);

        usuarioRepository.save(temp);

        mockMvc.perform(post("/usuarios")
                        .content(objectMapper.writeValueAsString(temp))
                        .contentType("application/json"))
                .andExpect(status().isCreated());


        mockMvc.perform(delete("/{usuarioId}", temp.getId()))
                .andExpect(status().isNoContent());

        Assertions.assertFalse(usuarioRepository.existsById(temp.getId()));
    }

    @Test
    public void testGetUsuarios() throws Exception {
        var res = mockMvc.perform(get("/listUsuarios")
                      )
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testReplaceUsuarioById() throws Exception {
        UsuarioRequestDto temp= new UsuarioRequestDto();
        temp.setNombre("cats");
        temp.setEmail("cat@cat.com");
        temp.setPassword("101010101010");

        mockMvc.perform(put("/{usuarioId}", usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(temp)))
                .andExpect(status().isOk());

        Optional<Usuario> updatedUsuario = usuarioRepository.findById(usuario.getId());
        Assertions.assertTrue(updatedUsuario.isPresent());
        Assertions.assertEquals(updatedUsuario.get().getEmail(), "cat@cat.com");
    }


}
