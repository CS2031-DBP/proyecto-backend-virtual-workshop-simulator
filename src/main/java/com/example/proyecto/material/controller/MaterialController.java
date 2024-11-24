package com.example.proyecto.material.controller;

import com.example.proyecto.material.domail.MaterialService;
import com.example.proyecto.material.dto.MaterialRequestDto;
import com.example.proyecto.material.dto.MaterialResponseDto;

import com.example.proyecto.post.dto.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/materiales")
public class MaterialController {

    private final MaterialService materialService;
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;

    }

    @PostMapping("/subir")
    public ResponseEntity<MaterialResponseDto> subirMaterial(@ModelAttribute MaterialRequestDto materialRequestDto,
                                                            @RequestParam("file") MultipartFile file) {
        MaterialResponseDto responseDto = materialService.subirMaterial(materialRequestDto, file);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{materialId}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long materialId) {
        materialService.deleteMaterial(materialId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<MaterialResponseDto>> getPosts(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MaterialResponseDto> materiales = materialService.getAll(pageable);
        return ResponseEntity.ok(materiales);
    }

}

