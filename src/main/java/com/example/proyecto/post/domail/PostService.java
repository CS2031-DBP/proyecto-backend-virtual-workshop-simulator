package com.example.proyecto.post.domail;

import com.example.proyecto.actividad.domail.Actividad;
import com.example.proyecto.actividad.infrastructure.ActividadRepository;
import com.example.proyecto.auth.config.AuthorizationConfig;
import com.example.proyecto.exception.ResourceNotFoundException;
import com.example.proyecto.exception.UnauthorizeOperationException;
import com.example.proyecto.material.domail.Material;
import com.example.proyecto.material.infrastructure.MaterialRepository;
import com.example.proyecto.post.dto.PostRequestDto;
import com.example.proyecto.post.dto.PostResponseDto;
import com.example.proyecto.post.infrastructure.PostRepository;
import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.dto.UsuarioResponseDto;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    public final PostRepository postRepository;
    public final ModelMapper modelMapper;
    public final UsuarioRepository usuarioRepository;
    public final MaterialRepository materialRepository;
    public final ActividadRepository actividadRepository;

    @Autowired
    private AuthorizationConfig authorizationConfig;

    public PostService(PostRepository postRepository,
                       ModelMapper modelMapper,
                       UsuarioRepository usuarioRepository,
                       MaterialRepository materialRepository,
                       ActividadRepository actividadRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.usuarioRepository = usuarioRepository;
        this.materialRepository = materialRepository;
        this.actividadRepository = actividadRepository;
    }

    public PostResponseDto createPost(PostRequestDto requestDto) {
        // Here get the current user identifier (email) using Spring Security
        String userEmail = authorizationConfig.getCurrentUserEmail();
        if(userEmail == null){
            throw new UnauthorizeOperationException("Not Allowed Random Users");
        }
        //
        Post post = new Post();
        modelMapper.map(requestDto, post);

        Usuario usuario = usuarioRepository.findById(requestDto.getUsuarioId()).
                orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado"));

        post.setUsuario(usuario);

        List<Material> materials = materialRepository.findAllById(requestDto.getMaterialesIds());
        post.setMateriales(materials);

        /*
        List<Actividad> actividades = actividadRepository.findAllById(requestDto.getActividadesIds());
        post.setActividades(actividades);*/

        postRepository.save(post);
        return modelMapper.map(post, PostResponseDto.class);
    }

    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado"));
        return modelMapper.map(post, PostResponseDto.class);
    }

    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        // Here get the current user identifier (email) using Spring Security
        String userEmail = authorizationConfig.getCurrentUserEmail();
        if(userEmail == null){
            throw new UnauthorizeOperationException("Not Allowed Random Users");
        }
        //
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado"));
        post.setTitulo(requestDto.getTitulo());
        postRepository.save(post);
        return modelMapper.map(post, PostResponseDto.class);
    }

    public void deletePost(Long id) {
        // Here get the current user identifier (email) using Spring Security
        String userEmail = authorizationConfig.getCurrentUserEmail();
        if(userEmail == null){
            throw new UnauthorizeOperationException("Not Allowed Random Users");
        }
        //
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado"));
        postRepository.delete(post);
    }



}
