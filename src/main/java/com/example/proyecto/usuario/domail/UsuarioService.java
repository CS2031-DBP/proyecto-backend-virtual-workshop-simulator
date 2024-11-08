package com.example.proyecto.usuario.domail;


import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.carrera.infrastructure.CarreraRepository;
import com.example.proyecto.exception.ResourceConflictException;
import com.example.proyecto.exception.ResourceNotFoundException;
import com.example.proyecto.usuario.dto.UsuarioRequestDto;
import com.example.proyecto.usuario.dto.UsuarioResponseDto;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CarreraRepository carreraRepository;
    private final ModelMapper modelMapper;
    public UsuarioService(UsuarioRepository usuarioRepository,
                          CarreraRepository carreraRepository,
                          ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.carreraRepository = carreraRepository;
        this.modelMapper = modelMapper;

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

    public UsuarioResponseDto inscribirse(Long usuarioId, Long carreraId){
        System.out.println(usuarioId);
        System.out.println(carreraId);
        Usuario usuario = usuarioRepository.findById(usuarioId).

                orElseThrow(()-> new ResourceNotFoundException("El usuario no existe"));

        Carrera carrera  =carreraRepository.findById(carreraId).
                orElseThrow(()-> new ResourceNotFoundException("El carrera no existe"));

        if (!usuario.getCarreras().contains(carrera)) {
            usuario.getCarreras().add(carrera);
            usuarioRepository.save(usuario);
            //System.out.println(usuario.getCarreras());
            return modelMapper.map(usuario, UsuarioResponseDto.class);

        }else{
            throw new ResourceConflictException("El usuario ya se encuentra inscrito en esta carrera");
        }

    }

    public List<UsuarioResponseDto> retornarByCarrera(){
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

    public UsuarioResponseDto reemplazarUsuario(Long id, UsuarioRequestDto usuarioRequestDto){
        Usuario usuario = usuarioRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado"));
        modelMapper.map(usuarioRequestDto, usuario);
        usuarioRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioResponseDto.class);
    }

    public void eliminar(Long id){
        Usuario usuario = usuarioRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }

    public Usuario findByEmail(String username, String role) {
        Usuario user;
        if (role.equals("ROLE_USER"))
            user = usuarioRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        else
            user = usuarioRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user;
    }
    @Bean(name = "UserDetailsService")
    public UserDetailsService userDetailsService() {
        return username -> {
            Usuario user = usuarioRepository
                    .findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return (UserDetails) user;
        };
    }


}