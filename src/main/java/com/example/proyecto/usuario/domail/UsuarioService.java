package com.example.proyecto.usuario.domail;


import com.example.proyecto.apis.amazonS3.AwsServices;
import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.carrera.infrastructure.CarreraRepository;
import com.example.proyecto.exception.ResourceConflictException;
import com.example.proyecto.exception.ResourceNotFoundException;
import com.example.proyecto.usuario.dto.Inscribirse;
import com.example.proyecto.usuario.dto.UsuarioRequestDto;
import com.example.proyecto.usuario.dto.UsuarioResponseDto;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CarreraRepository carreraRepository;
    private final ModelMapper modelMapper;
    private  final AwsServices awsServices;
    public UsuarioService(UsuarioRepository usuarioRepository,
                          CarreraRepository carreraRepository,
                          ModelMapper modelMapper,
                          AwsServices awsServices) {
        this.usuarioRepository = usuarioRepository;
        this.carreraRepository = carreraRepository;
        this.modelMapper = modelMapper;
        this.awsServices = awsServices;

    }

    public UsuarioResponseDto crear(UsuarioRequestDto usuarioRequestDto){

        if (usuarioRepository.findByEmail(usuarioRequestDto.getEmail()).isPresent()){
            throw new ResourceConflictException("El email ya existe");
        }

        Usuario usuario = modelMapper.map(usuarioRequestDto, Usuario.class);
        usuarioRepository.save(usuario);

        UsuarioResponseDto usuarioResponseDto = modelMapper.map(usuario, UsuarioResponseDto.class);
        return modelMapper.map(usuario, UsuarioResponseDto.class);
    }

    public String perfil(Long id, MultipartFile file) throws IOException {

        String keyName = "users/" + id + "/perfil";
        String url = awsServices.uploadFile(keyName, file);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        usuario.setPerfilUrl(url);
        usuarioRepository.save(usuario);

        return url;
    }

    public UsuarioResponseDto inscribirse(Inscribirse in){
        System.out.println(in.getUsuarioId());
        System.out.println(in.getCarreaId());

        Usuario usuario = usuarioRepository.findById(in.getUsuarioId()).
                orElseThrow(()-> new ResourceNotFoundException("El usuario no existe"));

        Carrera carrera  =carreraRepository.findById(in.getCarreaId()).
                orElseThrow(()-> new ResourceNotFoundException("El carrera no existe"));

        if (!usuario.getCarreras().contains(carrera)) {
            usuario.getCarreras().add(carrera);
            usuarioRepository.save(usuario);
            return modelMapper.map(usuario, UsuarioResponseDto.class);

        }else{
            throw new ResourceConflictException("El usuario ya se encuentra inscrito en esta carrera");
        }

    }

    public List<UsuarioResponseDto> retornarLista(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioResponseDto> usuarioResponseDtos = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioResponseDtos.add(modelMapper.map(usuario, UsuarioResponseDto.class));
        }
        return usuarioResponseDtos;
    }

    public UsuarioResponseDto retornarById(Long id){
        Usuario usuario = usuarioRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado"));
        return modelMapper.map(usuario, UsuarioResponseDto.class);

    }

    public UsuarioResponseDto editar(Long id, UsuarioRequestDto usuarioRequestDto, MultipartFile file) throws IOException {

        String keyName = "users/" + id + "/perfil";

        Usuario usuario = usuarioRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado"));

        String url = awsServices.uploadFile(keyName, file);
        usuario.setPerfilUrl(url);
        usuario.setNombre(usuarioRequestDto.getNombre());
        usuario.setEmail(usuarioRequestDto.getEmail());
        usuario.setPassword(usuarioRequestDto.getPassword());
        usuarioRepository.save(usuario);

        return modelMapper.map(usuario, UsuarioResponseDto.class);
    }

    public void eliminar(Long id){

        String keyName = "users/" + id + "/perfil";

        if (awsServices.fileExists(keyName)) {
            awsServices.DeleteFile(keyName);
        }

        Usuario usuario = usuarioRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado"));

        usuarioRepository.delete(usuario);
    }

    public Usuario findByEmail(String username) {
        return usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    @Bean(name = "UserDetailsService")
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }



}