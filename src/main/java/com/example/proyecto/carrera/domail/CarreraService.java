package com.example.proyecto.carrera.domail;

import com.example.proyecto.carrera.dto.CarreraRequestDto;
import com.example.proyecto.carrera.dto.CarreraResponseDto;
import com.example.proyecto.carrera.infrastructure.CarreraRepository;
import com.example.proyecto.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
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
            throw new ResourceNotFoundException("La carrera ya existe");
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

    public List<CarreraResponseDto> getAll(){
        List<Carrera> carreras = carreraRepository.findAll();
        List<CarreraResponseDto> carrerasDto = new ArrayList<>();
        for (Carrera carrera : carreras) {
            carrerasDto.add(modelMapper.map(carrera, CarreraResponseDto.class));
        }

        return carrerasDto;
    }

    public CarreraResponseDto updateCarrera(Long id, CarreraRequestDto requestDto) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));
        modelMapper.map(requestDto, carrera);
        carreraRepository.save(carrera);
        return modelMapper.map(carrera, CarreraResponseDto.class);
    }

    public void deleteCarrera(Long id) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));
        carreraRepository.delete(carrera);
    }
}

