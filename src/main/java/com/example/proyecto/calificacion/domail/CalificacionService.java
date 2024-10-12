package com.example.proyecto.calificacion.domail;

import com.example.proyecto.calificacion.dto.CalificacionRequestDto;
import com.example.proyecto.calificacion.infrastructure.CalificacionRepository;
import com.example.proyecto.exception.ResourceNotFoundException;
import com.example.proyecto.material.domail.Material;
import com.example.proyecto.material.dto.MaterialResponseDto;
import com.example.proyecto.material.infrastructure.MaterialRepository;
import com.example.proyecto.usuario.domail.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalificacionService {


    private final MaterialRepository materialRepository;
    private final CalificacionRepository calificacionRepository;
    private final ModelMapper modelMapper;

    public CalificacionService(MaterialRepository materialRepository,
                               CalificacionRepository calificacionRepository, ModelMapper modelMapper) {
       this.materialRepository = materialRepository;
       this.calificacionRepository = calificacionRepository;
        this.modelMapper = modelMapper;
    }

    public MaterialResponseDto calificarMaterial(CalificacionRequestDto request) {

        Optional<Calificacion> calificacionExistente = calificacionRepository.
                findByUsuarioIdAndMaterialId(request.getUsuarioId(), request.getMaterialId());
        if (calificacionExistente.isPresent()) {
            throw new IllegalArgumentException("El usuario ya ha calificado este material");
        }

        Material material = materialRepository.findById(request.getMaterialId())
                .orElseThrow(() -> new ResourceNotFoundException("Material no encontrado"));

        Calificacion nuevaCalificacion = new Calificacion();
        nuevaCalificacion.setValor(request.getValor());
        //nuevaCalificacion.setUsuario(new Usuario(request.getUsuarioId()));
        nuevaCalificacion.setMaterial(material);

        calificacionRepository.save(nuevaCalificacion);

        material.agregarCalificacion(request.getValor());
        materialRepository.save(material);

        return modelMapper.map(material, MaterialResponseDto.class);
    }
}
