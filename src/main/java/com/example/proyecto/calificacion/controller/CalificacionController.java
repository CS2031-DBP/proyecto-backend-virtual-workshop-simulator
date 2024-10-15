package com.example.proyecto.calificacion.controller;

import com.example.proyecto.calificacion.domail.CalificacionService;
import com.example.proyecto.calificacion.dto.CalificacionRequestDto;
import com.example.proyecto.material.dto.MaterialResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    @PostMapping()
    public ResponseEntity<MaterialResponseDto> calificarMaterial(@RequestBody CalificacionRequestDto request) {
        MaterialResponseDto materialResponse = calificacionService.calificarMaterial(request);
        return ResponseEntity.ok(materialResponse);
    }
}
