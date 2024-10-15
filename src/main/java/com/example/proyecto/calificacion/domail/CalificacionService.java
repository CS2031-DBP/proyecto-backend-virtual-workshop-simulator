package com.example.proyecto.calificacion.domail;

import com.example.proyecto.calificacion.dto.CalificacionRequestDto;
import com.example.proyecto.calificacion.infrastructure.CalificacionRepository;
import com.example.proyecto.exception.ResourceForbiddenException;
import com.example.proyecto.exception.ResourceNotFoundException;
import com.example.proyecto.material.domail.Material;
import com.example.proyecto.material.dto.MaterialResponseDto;
import com.example.proyecto.material.infrastructure.MaterialRepository;
import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalificacionService {


    private final MaterialRepository materialRepository;
    private final CalificacionRepository calificacionRepository;
    private final ModelMapper modelMapper;
    private final UsuarioRepository usuarioRepository;

    public CalificacionService(MaterialRepository materialRepository,
                               CalificacionRepository calificacionRepository, ModelMapper modelMapper, UsuarioRepository usuarioRepository) {
       this.materialRepository = materialRepository;
       this.calificacionRepository = calificacionRepository;
        this.modelMapper = modelMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public MaterialResponseDto calificarMaterial(CalificacionRequestDto request) {

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId()).
                orElseThrow( () -> new ResourceNotFoundException("Usuario no encontrado"));

        Material material = materialRepository.findById(request.getMaterialId()).
                orElseThrow(()-> new ResourceNotFoundException("Material no encontrado"));

        if (!usuario.getCarreras().contains(material.getCurso().getCarrera())){
            throw new ResourceForbiddenException("El usuario no esta inscrito en la carrera del curso de este material");
        }


        Optional<Calificacion> calificacionExistente = calificacionRepository.
                findByUsuarioIdAndMaterialId(request.getUsuarioId(), request.getMaterialId());


        if (calificacionExistente.isPresent()) {
            throw new IllegalArgumentException("El usuario ya ha calificado este material");
        }

        Calificacion nuevaCalificacion = new Calificacion();
        nuevaCalificacion.setValor(request.getValor());
        nuevaCalificacion.setUsuario(usuario);
        nuevaCalificacion.setMaterial(material);

        calificacionRepository.save(nuevaCalificacion);

        material.agregarCalificacion(request.getValor());
        materialRepository.save(material);

        return modelMapper.map(material, MaterialResponseDto.class);
    }
}
