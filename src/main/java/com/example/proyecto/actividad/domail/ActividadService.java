package com.example.proyecto.actividad.domail;

import com.example.proyecto.actividad.dto.ActividadRequestDto;
import com.example.proyecto.actividad.dto.ActividadResponseDto;
import com.example.proyecto.actividad.infrastructure.ActividadRepository;
import com.example.proyecto.apis.tinyUrl.TinyUrlService;
import com.example.proyecto.curso.domail.Curso;
import com.example.proyecto.curso.infrastructure.CursoRepository;
import com.example.proyecto.email.event.EmailEvent;
import com.example.proyecto.exception.ResourceNotFoundException;
import com.example.proyecto.post.infrastructure.PostRepository;
import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import com.example.proyecto.apis.whreby.WherebyService;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActividadService {

   private final ActividadRepository actividadRepository;
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final WherebyService wherebyService;
    private final UsuarioRepository usuarioRepository;
    private final TinyUrlService tinyUrlService;
    private final ApplicationEventPublisher eventPublisher;
    private final CursoRepository cursoRepository;

    public ActividadService(ActividadRepository actividadRepository,
                            ModelMapper modelMapper,
                            PostRepository postRepository,
                            WherebyService wherebyService,
                            UsuarioRepository usuarioRepository,
                            TinyUrlService tinyUrlService,
                            ApplicationEventPublisher eventPublisher, CursoRepository cursoRepository) {
        this.actividadRepository = actividadRepository;
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.wherebyService = wherebyService;
        this.usuarioRepository = usuarioRepository;
        this.tinyUrlService = tinyUrlService;
        this.eventPublisher = eventPublisher;
        this.cursoRepository = cursoRepository;
    }


    public ActividadResponseDto createActividad(Long usuarioid, Long cursoId, ActividadRequestDto requestDto) {
        Usuario usuario = usuarioRepository.findById(usuarioid)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Curso curso = cursoRepository.findById(cursoId).
                orElseThrow(()-> new ResourceNotFoundException("Curso no encontrado"));

        Actividad actividad = new Actividad();
        modelMapper.map(requestDto, actividad);
        actividad.setUsuario(usuario);
        actividad.setCurso(curso);

        if (requestDto.getTipo().toString() == "REUNION") {

            LocalDateTime fechaRegistro = requestDto.getFechaActividad().atTime(requestDto.getHora());
            actividad.setFechaActividad(fechaRegistro);
            try {
                String enlace = wherebyService.createWherebyMeeting(actividad.getFechaActividad());
                String enlaceShort = tinyUrlService.shortenUrl(enlace);
                actividad.setEnlace(enlaceShort);

            } catch(IOException | InterruptedException e){
                e.printStackTrace();
            }
            EmailEvent emailEvent = new EmailEvent(actividad);

            eventPublisher.publishEvent(emailEvent);

        } else if (requestDto.getTipo().toString() == "QUIZZ") {
            actividad.setEnlace("Generated Quiz Link");
        }
        usuario.getActividades().add(actividad);
        curso.getActividades().add(actividad);
        actividadRepository.save(actividad);

        return modelMapper.map(actividad, ActividadResponseDto.class);
    }

    public ActividadResponseDto getActividadById(Long id) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada"));
        return modelMapper.map(actividad, ActividadResponseDto.class);
    }

    public List<ActividadResponseDto> getAll(){
        List<Actividad> actividades = actividadRepository.findAll();
        List<ActividadResponseDto> act = new ArrayList<>();
        for (Actividad actividad:actividades){
            act.add(modelMapper.map(actividad,ActividadResponseDto.class));
        }
        return act;
    }

    public ActividadResponseDto updateActividad(Long id, ActividadRequestDto requestDto) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada"));

        modelMapper.map(requestDto, actividad);

        actividadRepository.save(actividad);
        return modelMapper.map(actividad, ActividadResponseDto.class);
    }

    public void deleteActividad(Long id) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada"));

        /*
        try {
            wherebyService.deleteWherebyMeeting(actividad.getEnlace());
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }

             */
        actividadRepository.delete(actividad);
    }
}