package com.example.proyecto.material.controller;

import com.example.proyecto.material.domail.MaterialService;
import com.example.proyecto.material.dto.MaterialRequestDto;
import com.example.proyecto.material.dto.MaterialResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/materiales")
public class MaterialController {

    private final MaterialService materialService;
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping
    public ResponseEntity<MaterialResponseDto> createMaterial(@RequestBody MaterialRequestDto materialRequestDto) {
        MaterialResponseDto response = materialService.createMaterial(materialRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponseDto> getMaterialById(@PathVariable Long id) {
        MaterialResponseDto response = materialService.getMaterialById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponseDto> updateMaterial(@PathVariable Long id, @RequestBody MaterialRequestDto materialRequestDto) {
        MaterialResponseDto response = materialService.updateMaterial(id, materialRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }
}

