package com.example.proyecto.post.domail;

import com.example.proyecto.actividad.domail.Actividad;
import com.example.proyecto.actividad.infrastructure.ActividadRepository;
import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.carrera.infrastructure.CarreraRepository;
import com.example.proyecto.exception.ResourceNotFoundException;
import com.example.proyecto.material.domail.Material;
import com.example.proyecto.material.infrastructure.MaterialRepository;
import com.example.proyecto.post.dto.PostRequestDto;
import com.example.proyecto.post.dto.PostResponseDto;
import com.example.proyecto.post.infrastructure.PostRepository;
import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.dto.UsuarioResponseDto;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PostService {

    public final PostRepository postRepository;
    public final ModelMapper modelMapper;
    public final UsuarioRepository usuarioRepository;
    public final MaterialRepository materialRepository;
    public final ActividadRepository actividadRepository;
    private final CarreraRepository carreraRepository;

    public PostService(PostRepository postRepository,
                       ModelMapper modelMapper,
                       UsuarioRepository usuarioRepository,
                       MaterialRepository materialRepository,
                       ActividadRepository actividadRepository, CarreraRepository carreraRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.usuarioRepository = usuarioRepository;
        this.materialRepository = materialRepository;
        this.actividadRepository = actividadRepository;
        this.carreraRepository = carreraRepository;
    }

    public PostResponseDto createPost(PostRequestDto requestDto) {

        Usuario usuario = usuarioRepository.findById(requestDto.getUsuarioId()).
                orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado"));

        Carrera carrera = carreraRepository.findById(requestDto.getCarreraId()).
                orElseThrow(()-> new ResourceNotFoundException("Carrera no encontrado"));

        Post post = new Post();
        modelMapper.map(requestDto, post);

        post.setUsuario(usuario);

        postRepository.save(post);

        PostResponseDto responseDto = modelMapper.map(post, PostResponseDto.class);
        responseDto.setAutorNombre(usuario.getNombre());

        return modelMapper.map(post, PostResponseDto.class);
    }

    public List<PostResponseDto> getAll(){
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> responseDtos = new ArrayList<>();
        for (Post post : posts) {
            responseDtos.add(modelMapper.map(post, PostResponseDto.class));
        }
        return responseDtos;
    }

    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado"));
        return modelMapper.map(post, PostResponseDto.class);
    }

    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado"));
        post.setTitulo(requestDto.getTitulo());
        postRepository.save(post);
        return modelMapper.map(post, PostResponseDto.class);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado"));
        postRepository.delete(post);
    }

}
