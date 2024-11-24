package com.example.proyecto.material.domail;

import com.example.proyecto.apis.amazonS3.AwsServices;
import com.example.proyecto.auth.config.AuthorizationConfig;
import com.example.proyecto.curso.domail.Curso;
import com.example.proyecto.curso.infrastructure.CursoRepository;
import com.example.proyecto.exception.ResourceConflictException;
import com.example.proyecto.exception.ResourceNotFoundException;
import com.example.proyecto.material.dto.MaterialRequestDto;
import com.example.proyecto.material.dto.MaterialResponseDto;
import com.example.proyecto.material.infrastructure.MaterialRepository;
import com.example.proyecto.post.dto.PostResponseDto;
import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final ModelMapper modelMapper;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final AwsServices awsServices;
    private final AuthorizationConfig authorizationConfig;

    public MaterialService(MaterialRepository materialRepository,
                           ModelMapper modelMapper,
                           UsuarioRepository usuarioRepository,
                           CursoRepository cursoRepository,
                           AwsServices awsServices,
                           AuthorizationConfig authorizationConfig) {
        this.materialRepository = materialRepository;
        this.modelMapper = modelMapper;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.awsServices = awsServices;
        this.authorizationConfig = authorizationConfig;
    }



    public MaterialResponseDto subirMaterial(MaterialRequestDto requestDto,
                                             MultipartFile file) {

        if (cursoRepository.findByNombre(requestDto.getNombre()).isPresent()){
            throw new ResourceConflictException("Archivo ya existe");
        }

        Curso curso = cursoRepository.findById(requestDto.getCursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        Usuario usuario = usuarioRepository.findById(requestDto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        try{
            String keyName = "users/" + requestDto.getUsuarioId() + "/material";

            String urlArchivo = awsServices.uploadFile(keyName, file);

            Material material = new Material();
            material.setNombre(requestDto.getNombre());
            material.setTipo(requestDto.getTipo());
            material.setUrlArchivo(urlArchivo);
            material.setCurso(curso);
            material.setPropietario(usuario);
            curso.getMateriales().add(material);

            materialRepository.save(material);

            MaterialResponseDto materialResponseDto = modelMapper.map(material, MaterialResponseDto.class);
            materialResponseDto.setUsuarioNombre(usuario.getNombre());

            return materialResponseDto;
        } catch (IOException e) {
            throw new RuntimeException("Error al almacenar el archivo", e);
        }
    }

    public void deleteMaterial(Long id) {

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material no encontrado"));

        String usuarioAuenticado = authorizationConfig.getCurrentUserEmail();

        if (!material.getPropietario().getEmail().equals(usuarioAuenticado)){
            throw new SecurityException("No tienes permiso para eliminar este material");
        }

        String keyName = "users/" + material.getPropietario().getId() + "/material/" + awsServices.getLastPart(material.getUrlArchivo());

        if (!awsServices.fileExists(keyName)) {
            throw new ResourceNotFoundException("El archivo no existe");
        }

        materialRepository.delete(material);
        awsServices.DeleteFile(keyName);

    }

    public Page<MaterialResponseDto> getAll(Pageable pageable) {
        return materialRepository.findAll(pageable)
                .map(material -> modelMapper.map(material, MaterialResponseDto.class));
    }


}
