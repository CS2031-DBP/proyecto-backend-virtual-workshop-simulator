package com.example.proyecto.curso.controller;

import com.example.proyecto.curso.domail.CursoService;
import com.example.proyecto.curso.dto.CursoRequestDto;
import com.example.proyecto.curso.dto.CursoResponseDto;
import com.example.proyecto.usuario.dto.UsuarioResponseDto;
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
    public ResponseEntity<CursoResponseDto> createCurso(@PathVariable Long carreraId,@RequestBody CursoRequestDto cursoRequestDto) {
        CursoResponseDto response = cursoService.createCurso(carreraId,cursoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<CursoResponseDto>> retornarByCarrera(){
        return ResponseEntity.ok(cursoService.retornarByCarrera());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDto> getCursoById(@PathVariable Long id) {
        CursoResponseDto response = cursoService.getCursoById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{CarreraId}/{id}")
    public ResponseEntity<CursoResponseDto> updateCurso(@PathVariable Long carreraId, @PathVariable Long id, @RequestBody CursoRequestDto cursoRequestDto) {
        CursoResponseDto response = cursoService.updateCurso(carreraId,id, cursoRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        cursoService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }
}
