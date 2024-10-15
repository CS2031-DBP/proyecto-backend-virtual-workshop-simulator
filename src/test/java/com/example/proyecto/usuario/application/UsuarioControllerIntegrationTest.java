package com.example.proyecto.usuario.application;

import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.carrera.infrastructure.CarreraRepository;
import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.dto.UsuarioRequestDto;
import com.example.proyecto.usuario.dto.UsuarioResponseDto;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
        usuario.setId(1L);
        usuario.setNombre("michis adeses");
        usuario.setEmail("roger.zavaleta@utec.edu.pe");
        usuario.setPassword("12345678");
        LocalDateTime temp_time = LocalDateTime.parse("2024-09-18T07:19:34");
        usuario.setFechaRegistro(temp_time);

        //carrera
        carrera = new Carrera();
        carrera.setId(1L);
        carrera.setNombre("Ciencia de la Computacion");
    }

    @Test
    public void testSaveUsuarioAndExpectCreated() throws Exception {
//        MvcResult result  = MockMvcRequestBuilders.post("/usuarios")
//                        .content(objectMapper.writeValueAsString(usuario))
//                        .contentType("application/json");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/usuarios").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario))).andReturn();

        MockHttpServletResponse response = result.getResponse();

        JSONObject temp = new JSONObject(
                response.getContentAsString()
        );

        Assertions.assertEquals(201, response.getStatus());

        Usuario updatedUsuario = usuarioRepository.findAll().get(0);
        Assertions.assertEquals(updatedUsuario.getId(),temp.getLong("id"));
        Assertions.assertEquals(temp.get("email"),updatedUsuario.getEmail());
        Assertions.assertEquals(temp.get("nombre"),updatedUsuario.getNombre());
    }

    @Test
    public void testSaveUsuarioAndIscribirCarrera() throws Exception {
//        carreraRepository.save(carrera);
//
//        mockMvc.perform(post("/usuarios/1/carreras/1/inscribir",usuario.getId(),carrera.getId())
//                )
//                .andExpect(status().isOk());
//        //test añadir carrera a usuario
//        Usuario updatedUsuario = usuarioRepository.findAll().get(0);
//        Assertions.assertEquals(usuario.getEmail(),updatedUsuario.getEmail());
//        Assertions.assertEquals(carrera.getNombre(),updatedUsuario.getCarreras().get(0).getNombre());
//
//        //test error
//        Optional<Usuario> savedUsuario = usuarioRepository.findById((usuario.getId()));
//        Assertions.assertTrue(savedUsuario.isPresent());
//
//        mockMvc.perform(post("/usuarios/{usuarioId}/carreras/{carreraId}/inscribir",usuario.getId(),carrera.getId())
//                )
//                .andExpect(status().isConflict());
//
//
        //añadir carrera al repositorio
        carreraRepository.save(carrera);
        //realizar request
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/usuarios/{usuarioId}/carreras/{carreraId}/inscribir").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario))).andReturn();

        MockHttpServletResponse response = result.getResponse();
        //test añadir carrera a usuario
        Usuario updatedUsuario = usuarioRepository.findAll().get(0);
        Assertions.assertEquals(usuario.getEmail(),updatedUsuario.getEmail());
        Assertions.assertEquals(carrera.getNombre(),updatedUsuario.getCarreras().get(0).getNombre());

        //test error
        Optional<Usuario> savedUsuario = usuarioRepository.findById((usuario.getId()));
        Assertions.assertTrue(savedUsuario.isPresent());
    }

    @Test
    public void testGetUsuario() throws Exception {
        Usuario temp= new Usuario();
        temp.setId(1L);
        temp.setNombre("michis adeses");
        temp.setEmail("ra@utec.edu.pe");
        temp.setPassword("12345678");
        LocalDateTime temp_time = LocalDateTime.parse("2024-09-18T07:19:34");
        temp.setFechaRegistro(temp_time);


//        mockMvc.perform(post("/usuarios")
//                        .content(objectMapper.writeValueAsString(temp))
//                        .contentType("application/json"))
//                .andExpect(status().isCreated());
//
//        Assertions.assertEquals(usuarioRepository.findById(2L), 2 );
//
//
//        mockMvc.perform(get("/usuarios/{usuarioId}",2L).param("usuarioId", "1"))
//                .andExpect(status().isOk());
        /////
        //request 1
        MvcResult request1 = mockMvc
                .perform(MockMvcRequestBuilders.post("/usuarios").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(temp))).andReturn();

        MockHttpServletResponse response1 = request1.getResponse();
        Assertions.assertEquals(201, response1.getStatus());

        JSONObject res1 = new JSONObject(
                response1.getContentAsString()
        );
        //request 2
        MvcResult request2 = mockMvc
                .perform(MockMvcRequestBuilders.get("/usuarios/{usuarioId}",res1.getLong("id"))).andReturn();

        MockHttpServletResponse response2 = request2.getResponse();
        Assertions.assertEquals(200, response2.getStatus());

        JSONObject res2 = new JSONObject(
                response2.getContentAsString()
        );
        //test si el response es el usuario
        Assertions.assertEquals(res1.getLong("id"), res2.getLong("id"));
        Assertions.assertEquals(res1.getString("email"), res2.getString("email"));

    }

    @Test
    public void testDeleteDriver() throws Exception {
        Usuario temp= new Usuario();
        temp.setId(2L);
        temp.setNombre("michis adeses");
        temp.setEmail("roger.zavaleta@utec.edu.pe");
        temp.setPassword("12345678");
        LocalDateTime temp_time = LocalDateTime.parse("2024-09-18T07:19:34");
        temp.setFechaRegistro(temp_time);

        mockMvc.perform(post("/usuarios")
                        .content(objectMapper.writeValueAsString(temp))
                        .contentType("application/json"))
                .andExpect(status().isCreated());


        mockMvc.perform(delete("/usuarios/{usuarioId}", temp.getId()))
                .andExpect(status().isNoContent());

        Assertions.assertFalse(usuarioRepository.existsById(temp.getId()));
    }

    @Test
    public void testGetUsuarios() throws Exception {
         mockMvc.perform(get("/usuarios/listUsuarios")
                      )
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testReplaceUsuarioById() throws Exception {
        UsuarioRequestDto temp= new UsuarioRequestDto();
        temp.setNombre("cats");
        temp.setEmail("cat@cat.com");
        temp.setPassword("101010101010");

        mockMvc.perform(put("/usuarios/{usuarioId}", usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(temp)))
                .andExpect(status().isOk());

        Optional<Usuario> updatedUsuario = usuarioRepository.findById(usuario.getId());
        Assertions.assertTrue(updatedUsuario.isPresent());
        Assertions.assertEquals(updatedUsuario.get().getEmail(), "cat@cat.com");
    }


}
