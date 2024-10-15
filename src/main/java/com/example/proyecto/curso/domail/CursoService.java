package com.example.proyecto.curso.domail;

import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.carrera.infrastructure.CarreraRepository;
import com.example.proyecto.curso.dto.CursoRequestDto;
import com.example.proyecto.curso.dto.CursoResponseDto;
import com.example.proyecto.curso.infrastructure.CursoRepository;
import com.example.proyecto.exception.ResourceConflictException;
import com.example.proyecto.exception.ResourceNotFoundException;
import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.dto.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CarreraRepository carreraRepository;
    private final ModelMapper modelMapper;
    public CursoService(CursoRepository cursoRepository,
                        CarreraRepository carreraRepository,
                        ModelMapper modelMapper) {
        this.cursoRepository = cursoRepository;
        this.carreraRepository = carreraRepository;
        this.modelMapper = modelMapper;
    }


    public CursoResponseDto createCurso(Long carreraId,CursoRequestDto requestDto) {

        if (cursoRepository.findByNombre(requestDto.getNombre()).isPresent()) {
            throw new ResourceConflictException("Curso ya existente");
        }
        Carrera carrera = carreraRepository.findById(carreraId)
                .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));

        Curso curso = new Curso();
        modelMapper.map(requestDto, curso);

        curso.setCarrera(carrera);
        //carrera.getCursos().add(curso);

        cursoRepository.save(curso);
        return modelMapper.map(curso, CursoResponseDto.class);
    }

    public List<CursoResponseDto> retornarByCarrera(){
        List<Curso> cursos = cursoRepository.findAll();
        List<CursoResponseDto> cursosResponseDto = new ArrayList<>();
        for (Curso curso : cursos) {
            cursosResponseDto.add(modelMapper.map(curso, CursoResponseDto.class));
        }
        return cursosResponseDto;
    }

    public CursoResponseDto getCursoById(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));
        return modelMapper.map(curso, CursoResponseDto.class);
    }

    public CursoResponseDto updateCurso(Long carreraId,Long id, CursoRequestDto requestDto) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));
        modelMapper.map(requestDto, curso);

        Carrera carrera = carreraRepository.findById(carreraId)
                .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));
        curso.setCarrera(carrera);

        cursoRepository.save(curso);
        return modelMapper.map(curso, CursoResponseDto.class);
    }

    public void deleteCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));
        cursoRepository.delete(curso);
    }
}

