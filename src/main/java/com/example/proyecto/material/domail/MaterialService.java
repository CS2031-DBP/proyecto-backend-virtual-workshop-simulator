package com.example.proyecto.material.domail;

import com.example.proyecto.curso.domail.Curso;
import com.example.proyecto.curso.infrastructure.CursoRepository;
import com.example.proyecto.exception.ResourceNotFoundException;
import com.example.proyecto.material.dto.MaterialRequestDto;
import com.example.proyecto.material.dto.MaterialResponseDto;
import com.example.proyecto.material.infrastructure.MaterialRepository;
import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    public MaterialResponseDto createMaterial(MaterialRequestDto requestDto) {
        Material material = new Material();
        modelMapper.map(requestDto, material);

        Usuario usuario = usuarioRepository.findById(requestDto.getUsuarioId()).
                orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado"));
        material.setUsuario(usuario);

        Curso curso = cursoRepository.findById(requestDto.getCursoId()).
                orElseThrow(()-> new ResourceNotFoundException("Curso No encontrado"));
        material.setCurso(curso);

        materialRepository.save(material);
        return modelMapper.map(material, MaterialResponseDto.class);
    }

    public MaterialResponseDto getMaterialById(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material no encontrado"));
        return modelMapper.map(material, MaterialResponseDto.class);
    }

    public MaterialResponseDto updateMaterial(Long id, MaterialRequestDto requestDto) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material no encontrado"));
        material.setNombre(requestDto.getNombre());
        material.setUrl(requestDto.getUrl());
        material.setTipo(requestDto.getTipo());

        materialRepository.save(material);
        return modelMapper.map(material, MaterialResponseDto.class);
    }

    public void deleteMaterial(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material no encontrado"));
        materialRepository.delete(material);
    }
}

