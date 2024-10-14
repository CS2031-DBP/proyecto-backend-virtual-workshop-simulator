package com.example.proyecto.usuario.controller;

import com.example.proyecto.usuario.domail.UsuarioService;
import com.example.proyecto.usuario.dto.UsuarioRequestDto;
import com.example.proyecto.usuario.dto.UsuarioResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> crear(@RequestBody UsuarioRequestDto usuarioRequestDto){
        UsuarioResponseDto usuarioResponseDto = usuarioService.crear(usuarioRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponseDto);
    }

    @PostMapping("/{usuarioId}/carreras/{carreraId}/inscribir")
    public ResponseEntity<UsuarioResponseDto> inscripcion(@PathVariable Long usuarioId,
                                                          @PathVariable Long carreraId){
        return ResponseEntity.ok(usuarioService.inscribirse(usuarioId, carreraId));
    }

    @GetMapping("/listUsuarios")
    public ResponseEntity<List<UsuarioResponseDto>> retornarByCarrera(){
        return ResponseEntity.ok(usuarioService.retornarByCarrera());
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDto> retornarById(@RequestParam Long usuarioId){
        return ResponseEntity.ok(usuarioService.retornarById(usuarioId));
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDto> reemplazarUsuario(@PathVariable Long usuarioId,
                                                  @RequestBody UsuarioRequestDto usuarioRequestDto){
        return ResponseEntity.ok(usuarioService.reemplazarUsuario(usuarioId, usuarioRequestDto));
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> delete(@PathVariable Long usuarioId){
        usuarioService.eliminar(usuarioId);
        return ResponseEntity.noContent().build();
    }

}
