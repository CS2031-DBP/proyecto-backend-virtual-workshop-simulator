package com.example.proyecto.post.domail;

import com.example.proyecto.actividad.infrastructure.ActividadRepository;
import com.example.proyecto.auth.config.AuthorizationConfig;
import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.carrera.infrastructure.CarreraRepository;
import com.example.proyecto.exception.ResourceNotFoundException;
import com.example.proyecto.exception.UnauthorizeOperationException;
import com.example.proyecto.material.infrastructure.MaterialRepository;
import com.example.proyecto.post.dto.PostRequestDto;
import com.example.proyecto.post.dto.PostResponseDto;
import com.example.proyecto.post.dto.PostUpdate;
import com.example.proyecto.post.infrastructure.PostRepository;
import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;

@Service
public class PostService {

    public final PostRepository postRepository;
    public final ModelMapper modelMapper;
    public final UsuarioRepository usuarioRepository;
    public final MaterialRepository materialRepository;
    public final ActividadRepository actividadRepository;
    private final CarreraRepository carreraRepository;
    private final AuthorizationConfig authorizationConfig;

    public PostService(PostRepository postRepository,
                       ModelMapper modelMapper,
                       UsuarioRepository usuarioRepository,
                       MaterialRepository materialRepository,
                       ActividadRepository actividadRepository,
                       CarreraRepository carreraRepository,
                       AuthorizationConfig authorizationConfig) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.usuarioRepository = usuarioRepository;
        this.materialRepository = materialRepository;
        this.actividadRepository = actividadRepository;
        this.carreraRepository = carreraRepository;
        this.authorizationConfig = authorizationConfig;
    }

    public PostResponseDto createPost(PostRequestDto requestDto) {

        String userEmail = authorizationConfig.getCurrentUserEmail();
        if(userEmail == null){
            throw new UnauthorizeOperationException("Not Allowed Random Users");
        }
        Usuario usuario = usuarioRepository.findById(requestDto.getUsuarioId()).
                orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado"));

        Carrera carrera = carreraRepository.findById(requestDto.getCarreraId()).
                orElseThrow(()-> new ResourceNotFoundException("Carrera no encontrado"));

        if (!usuario.getCarreras().contains(carrera)) {
            throw new UnauthorizeOperationException("No puedes hacer publicaciones. No está inscrito en la carrera.");
        }

        Post post = new Post();
        post.setAutor(usuario);
        post.setCarrera(carrera);
        post.setTitulo(requestDto.getTitulo());
        post.setContenido(requestDto.getContenido());
        postRepository.save(post);
        PostResponseDto postdto = modelMapper.map(post, PostResponseDto.class);
        postdto.setAutorNombre(usuario.getNombre());
        postdto.setFechaCreacion(post.getFechaCreacion());

        return postdto;

    }

    public Page<PostResponseDto> getAll(Long carreraId , Pageable pageable) {
        Carrera carrera = carreraRepository.findById(carreraId).orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrado"));
        return postRepository.findByCarrera(carrera, pageable)
                .map(post -> modelMapper.map(post, PostResponseDto.class));
    }

    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado"));
        return modelMapper.map(post, PostResponseDto.class);
    }

    public PostResponseDto updatePost(Long id, PostUpdate requestDto) {
        String userEmail = authorizationConfig.getCurrentUserEmail();
        if(userEmail == null){
            throw new UnauthorizeOperationException("No se encuentra AUTORIZADO!!");
        }

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado"));

        if (!post.getAutor().getEmail().equals(userEmail)) {
            throw new UnauthorizeOperationException("No puedes actualizar este post");
        }

        post.setTitulo(requestDto.getTitulo());
        post.setContenido(requestDto.getContenido());
        postRepository.save(post);
        return modelMapper.map(post, PostResponseDto.class);
    }

    public void deletePost(Long id) {
        String userEmail = authorizationConfig.getCurrentUserEmail();
        if(userEmail == null){
            throw new UnauthorizeOperationException("No tiene Autorizacion");
        }

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado"));

        Usuario usuario = usuarioRepository.findByEmail(userEmail).
                orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado"));

        if (!usuario.getCarreras().contains(post.getCarrera())) {
            throw new UnauthorizeOperationException("No puedes hacer publicaciones. No está inscrito en la carrera.");
        }

        if (!post.getAutor().getEmail().equals(userEmail)) {
            throw new UnauthorizeOperationException("No puedes eliminar este post");
        }

        postRepository.delete(post);
    }

}
