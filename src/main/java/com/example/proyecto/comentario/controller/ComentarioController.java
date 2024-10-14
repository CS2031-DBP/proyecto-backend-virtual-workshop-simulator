package com.example.proyecto.comentario.controller;

import com.example.proyecto.comentario.domail.ComentarioService;
import com.example.proyecto.comentario.dto.ComentarioRequestDto;
import com.example.proyecto.comentario.dto.ComentarioResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService){
        this.comentarioService = comentarioService;
    }

    @PostMapping()
    public ResponseEntity<ComentarioResponseDto> createComentario(@RequestBody ComentarioRequestDto comentarioRequestDto) {
        ComentarioResponseDto response = comentarioService.createComentario(comentarioRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioResponseDto> getComentarioById(@PathVariable Long id) {
        ComentarioResponseDto response = comentarioService.getComentarioById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<ComentarioResponseDto>> getAllComentarios() {
        return ResponseEntity.ok(comentarioService.getAllComentarios());
    }

    /*
    @PutMapping("/{id}")
    public ResponseEntity<ComentarioResponseDto> updateComentario(@PathVariable Long id, @RequestBody ComentarioRequestDto comentarioRequestDto) {
        ComentarioResponseDto response = comentarioService.updateComentario(id, comentarioRequestDto);
        return ResponseEntity.ok(response);
    }
    */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComentario(@PathVariable Long id) {
        comentarioService.deleteComentario(id);
        return ResponseEntity.noContent().build();
    }
}
