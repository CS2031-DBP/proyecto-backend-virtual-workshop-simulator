package com.example.proyecto.carrera.domail;

import com.example.proyecto.carrera.dto.CarreraRequestDto;
import com.example.proyecto.carrera.dto.CarreraResponseDto;
import com.example.proyecto.carrera.infrastructure.CarreraRepository;
import com.example.proyecto.exception.ResourceConflictException;
import com.example.proyecto.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarreraService {

    private final CarreraRepository carreraRepository;
    private ModelMapper modelMapper;
    public CarreraService(CarreraRepository carreraRepository,
                          ModelMapper modelMapper) {
        this.carreraRepository = carreraRepository;
        this.modelMapper = modelMapper;
    }

    public CarreraResponseDto createCarrera(CarreraRequestDto requestDto) {

        if (carreraRepository.findByNombre(requestDto.getNombre()).isPresent()){
            throw new ResourceConflictException("La carrera ya existe");
        }
        Carrera carrera = new Carrera();
        modelMapper.map(requestDto, carrera);
        carreraRepository.save(carrera);
        return modelMapper.map(carrera, CarreraResponseDto.class);
    }

    public CarreraResponseDto getCarreraById(Long id) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));
        return modelMapper.map(carrera, CarreraResponseDto.class);
    }

    public Page<CarreraResponseDto> getAll(Pageable pageable){
        return carreraRepository.findAll(pageable)
                .map(carrera -> modelMapper.map(carrera, CarreraResponseDto.class));
    }

    public CarreraResponseDto updateCarrera(Long id, CarreraRequestDto requestDto) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));

        carrera.setNombre(requestDto.getNombre());
        return modelMapper.map(carrera, CarreraResponseDto.class);
    }

    public void deleteCarrera(Long id) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));
        carreraRepository.delete(carrera);
    }
}

