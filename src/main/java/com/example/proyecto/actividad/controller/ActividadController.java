package com.example.proyecto.actividad.controller;
import com.example.proyecto.actividad.domail.ActividadService;
import com.example.proyecto.actividad.dto.ActividadRequestDto;
import com.example.proyecto.actividad.dto.ActividadResponseDto;
import com.example.proyecto.apis.whreby.WherebyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actividades")
public class ActividadController {

    private final ActividadService actividadService;

    private final WherebyService wherebyService;

    public ActividadController(ActividadService actividadService
            , WherebyService wherebyService) {
        this.actividadService = actividadService;
        this.wherebyService = wherebyService;
    }

    @PostMapping("/{usuarioId}/curso/{cursoId}")
    public ResponseEntity<ActividadResponseDto> createActividad(@PathVariable Long usuarioId,
                                                                @PathVariable Long cursoId,
                                                                @RequestBody ActividadRequestDto actividadRequestDto) {
        ActividadResponseDto response = actividadService.createActividad(usuarioId, cursoId, actividadRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("curso/{cursoId}")
    public ResponseEntity<List<ActividadResponseDto>> getAll(@PathVariable Long cursoId) {
        List<ActividadResponseDto> response = actividadService.getAll(cursoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{usuarioId}/{id}")
    public ResponseEntity<ActividadResponseDto> getActividadById(@PathVariable Long usuarioId,
                                                                 @PathVariable Long id) {
        ActividadResponseDto response = actividadService.getActividadById(usuarioId,id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActividadResponseDto> updateActividad(@PathVariable Long id, @RequestBody ActividadRequestDto actividadRequestDto) {
        ActividadResponseDto response = actividadService.updateActividad(id, actividadRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActividad(@PathVariable Long id) {
        actividadService.deleteActividad(id);
        return ResponseEntity.noContent().build();
    }
}

