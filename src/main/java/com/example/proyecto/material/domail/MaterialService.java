package com.example.proyecto.material.domail;

import com.example.proyecto.curso.domail.Curso;
import com.example.proyecto.curso.infrastructure.CursoRepository;
import com.example.proyecto.exception.ResourceConflictException;
import com.example.proyecto.exception.ResourceNotFoundException;
import com.example.proyecto.material.dto.MaterialRequestDto;
import com.example.proyecto.material.dto.MaterialResponseDto;
import com.example.proyecto.material.infrastructure.MaterialRepository;
import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final ModelMapper modelMapper;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public MaterialService(MaterialRepository materialRepository, ModelMapper modelMapper, UsuarioRepository usuarioRepository, CursoRepository cursoRepository) {
        this.materialRepository = materialRepository;
        this.modelMapper = modelMapper;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }


    public MaterialResponseDto subirMaterial(Long cursoId,
                                             Long usuarioId,
                                             MaterialRequestDto requestDto) {

        if (cursoRepository.findByNombre(requestDto.getNombre()).isPresent()){
            throw new ResourceConflictException("Archivo ya existe");
        }

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        //Almacenar el archivo  AWS S3
        try{
            //String urlArchivo = almacenarArchivo(requestDto.getArchivo());

            String urlArchivo = awsServices.uploadFile(requestDto.getArchivo());

            Material material = new Material();
            material.setNombre(requestDto.getNombre());
            material.setTipo(requestDto.getTipo());
            material.setUrlArchivo(urlArchivo);
            material.setCurso(curso);
            material.setUsuario(usuario);

            materialRepository.save(material);

            MaterialResponseDto materialResponseDto = modelMapper.map(material, MaterialResponseDto.class);
            materialResponseDto.setUsuarioNombre(usuario.getNombre());

            return materialResponseDto;
        } catch (IOException e) {
            throw new RuntimeException("Error al almacenar el archivo", e);
        }
    }

    private String almacenarArchivo(MultipartFile archivo) throws IOException {

        if (archivo == null || archivo.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío o es nulo");
        }

        File directorio = new File("src/main/resources/archivos/");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        Path rutaArchivo = Paths.get("src/main/resources/archivos/" + archivo.getOriginalFilename());

        Files.write(rutaArchivo, archivo.getBytes());

        return "http://localhost:8080/archivos/" + archivo.getOriginalFilename();
    }

    public void deleteMaterial(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material no encontrado"));
        materialRepository.delete(material);

        eliminarArchivoLocal(material.getUrlArchivo());
    }

    private void eliminarArchivoLocal(String urlArchivo) {
        try {
            Path path = Paths.get("src/main/resources/archivos/", urlArchivo.substring(urlArchivo.lastIndexOf("/") + 1));
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar el archivo local: " + e.getMessage(), e);
        }
    }

}

