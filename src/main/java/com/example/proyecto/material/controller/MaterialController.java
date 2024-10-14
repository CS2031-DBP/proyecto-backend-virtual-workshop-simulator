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

    @PostMapping("/{cursoId}/{usuarioId}/subir")
    public ResponseEntity<MaterialResponseDto> subirMaterial(
            @PathVariable Long cursoId,
            @PathVariable Long usuarioId,
            @ModelAttribute MaterialRequestDto requestDto) {
        MaterialResponseDto responseDto = materialService.subirMaterial(cursoId, usuarioId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }

}

