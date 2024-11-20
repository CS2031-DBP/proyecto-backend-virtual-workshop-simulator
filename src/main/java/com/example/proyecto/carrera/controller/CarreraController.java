package com.example.proyecto.carrera.controller;

import com.example.proyecto.carrera.domail.CarreraService;
import com.example.proyecto.carrera.dto.CarreraRequestDto;
import com.example.proyecto.carrera.dto.CarreraResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<Page<CarreraResponseDto>> getAllCarreras(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CarreraResponseDto> carreras = carreraService.getAll(pageable);
        return ResponseEntity.ok(carreras);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarreraResponseDto> updateCarrera(@PathVariable Long id,
                                                            @RequestBody CarreraRequestDto carreraRequestDto) {
        CarreraResponseDto response = carreraService.updateCarrera(id, carreraRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrera(@PathVariable Long id) {
        carreraService.deleteCarrera(id);
        return ResponseEntity.noContent().build();
    }
}

