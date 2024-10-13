package com.example.proyecto.material.controller;

import com.example.proyecto.material.domail.MaterialService;
import com.example.proyecto.material.dto.MaterialRequestDto;
import com.example.proyecto.material.dto.MaterialResponseDto;
import org.springframework.http.HttpStatus;
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

    /*

    @PostMapping
    public ResponseEntity<MaterialResponseDto> createMaterial(@RequestBody MaterialRequestDto materialRequestDto) {
        MaterialResponseDto response = materialService.createMaterial(materialRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/pictures")
    public ResponseEntity<String> uploadPicture(MultipartFile file) throws Exception {

        return new ResponseEntity<>(materialService.uploadPicture(file), HttpStatus.OK);
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
     */

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

