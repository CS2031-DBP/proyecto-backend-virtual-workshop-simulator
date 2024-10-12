package com.example.proyecto.curso.domail;

import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.carrera.infrastructure.CarreraRepository;
import com.example.proyecto.curso.dto.CursoRequestDto;
import com.example.proyecto.curso.dto.CursoResponseDto;
import com.example.proyecto.curso.infrastructure.CursoRepository;
import com.example.proyecto.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        Curso curso = new Curso();
        modelMapper.map(requestDto, curso);

        Carrera carrera = carreraRepository.findById(carreraId)
                .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));
        curso.setCarrera(carrera);
        carrera.getCursos().add(curso);

        cursoRepository.save(curso);
        return modelMapper.map(curso, CursoResponseDto.class);
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

