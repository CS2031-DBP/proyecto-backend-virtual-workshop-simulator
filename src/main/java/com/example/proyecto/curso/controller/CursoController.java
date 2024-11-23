package com.example.proyecto.curso.controller;

import com.example.proyecto.curso.domail.Curso;
import com.example.proyecto.curso.domail.CursoService;
import com.example.proyecto.curso.dto.CursoRequestAsignarDto;
import com.example.proyecto.curso.dto.CursoRequestDto;
import com.example.proyecto.curso.dto.CursoResponseDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService){
        this.cursoService = cursoService;
    }


    @PostMapping("/{carreraId}")
    public ResponseEntity<CursoResponseDto> createCurso(@PathVariable Long carreraId,
                                                        @RequestBody CursoRequestDto cursoRequestDto) {
        CursoResponseDto response = cursoService.createCurso(carreraId,cursoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("asignar/")
    public ResponseEntity<Curso> createCurso(@RequestBody CursoRequestAsignarDto cursoRequestAsignarDto) {
        Curso response = cursoService.AsignarCurso(cursoRequestAsignarDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<Page<CursoResponseDto>> retornarByCarrera(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<CursoResponseDto> cursos = cursoService.retornarByCarrera(pageable);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDto> getCursoById(@PathVariable Long id) {
        CursoResponseDto response = cursoService.getCursoById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDto> updateCurso(@PathVariable Long id,
                                                        @RequestBody CursoRequestDto cursoRequestDto) {
        CursoResponseDto response = cursoService.updateCurso(id, cursoRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        cursoService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }
}
