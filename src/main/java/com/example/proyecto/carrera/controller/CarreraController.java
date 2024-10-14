package com.example.proyecto.carrera.controller;

import com.example.proyecto.carrera.domail.CarreraService;
import com.example.proyecto.carrera.dto.CarreraRequestDto;
import com.example.proyecto.carrera.dto.CarreraResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    private final CarreraService carreraService;
    public CarreraController(CarreraService carreraService){
        this.carreraService = carreraService;
    }

    @PostMapping
    public ResponseEntity<CarreraResponseDto> createCarrera(@RequestBody CarreraRequestDto carreraRequestDto) {
        CarreraResponseDto response = carreraService.createCarrera(carreraRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarreraResponseDto> getCarreraById(@PathVariable Long id) {
        CarreraResponseDto response = carreraService.getCarreraById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CarreraResponseDto>> getAllCarreras() {
        return ResponseEntity.ok(carreraService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarreraResponseDto> updateCarrera(@PathVariable Long id, @RequestBody CarreraRequestDto carreraRequestDto) {
        CarreraResponseDto response = carreraService.updateCarrera(id, carreraRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrera(@PathVariable Long id) {
        carreraService.deleteCarrera(id);
        return ResponseEntity.noContent().build();
    }
}

