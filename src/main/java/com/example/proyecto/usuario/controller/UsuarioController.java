package com.example.proyecto.usuario.controller;

import com.example.proyecto.usuario.domail.UsuarioService;
import com.example.proyecto.usuario.dto.Inscribirse;
import com.example.proyecto.usuario.dto.UsuarioRequestDto;
import com.example.proyecto.usuario.dto.UsuarioResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/{id}/perfil")
    public ResponseEntity<String> perfil(@PathVariable Long id,
                                         @RequestParam("file")MultipartFile file){
        try{

            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.perfil(id, file));

        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se pudo subir su perfil");
        }

    }

    @PostMapping("/carreras/inscribir")
    public ResponseEntity<UsuarioResponseDto> inscripcion(@RequestBody Inscribirse in){
        return ResponseEntity.ok(usuarioService.inscribirse(in));
    }

    @GetMapping("/listUsuarios")
    public ResponseEntity<List<UsuarioResponseDto>> retornarLista(){
        return ResponseEntity.ok(usuarioService.retornarLista());
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDto> retornarById(@RequestParam Long usuarioId){
        return ResponseEntity.ok(usuarioService.retornarById(usuarioId));
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDto> editar(@PathVariable Long usuarioId,
                                                     @RequestBody UsuarioRequestDto usuarioRequestDto,
                                                     @RequestParam MultipartFile file){

        try {

            if (file.getContentType() != "image/jpeg" || file.getContentType() != "image/png") {
                throw new IllegalArgumentException("Tipo de archivo no permitido");
            }

            return ResponseEntity.ok(usuarioService.editar(usuarioId, usuarioRequestDto, file));

        }catch (Exception e){
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> delete(@PathVariable Long usuarioId){
        usuarioService.eliminar(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/perfil")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        usuarioService.eliminarPerfil(id);
        return ResponseEntity.noContent().build();
    }

}
