package com.example.proyecto.curso.domail;

import com.example.proyecto.actividad.domail.Actividad;
import com.example.proyecto.actividad.dto.ActividadResponseDto;
import com.example.proyecto.actividad.infrastructure.ActividadRepository;
import com.example.proyecto.auth.config.AuthorizationConfig;
import com.example.proyecto.carrera.domail.Carrera;
import com.example.proyecto.carrera.infrastructure.CarreraRepository;
import com.example.proyecto.curso.dto.CursoRequestAsignarDto;
import com.example.proyecto.curso.dto.CursoRequestDto;
import com.example.proyecto.curso.dto.CursoResponseDto;
import com.example.proyecto.curso.infrastructure.CursoRepository;
import com.example.proyecto.exception.ResourceConflictException;
import com.example.proyecto.exception.ResourceNotFoundException;
import com.example.proyecto.exception.UnauthorizeOperationException;
import com.example.proyecto.post.domail.Post;
import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.dto.UsuarioCursosResDTO;
import com.example.proyecto.usuario.dto.UsuarioResponseDto;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CarreraRepository carreraRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationConfig authorizationConfig;
    private final UsuarioRepository usuarioRepository;
    private final ActividadRepository actividadRepository;

    public CursoService(CursoRepository cursoRepository,
                        CarreraRepository carreraRepository,
                        ModelMapper modelMapper,
                        AuthorizationConfig authorizationConfig,
                        UsuarioRepository usuarioRepository, ActividadRepository actividadRepository) {
        this.cursoRepository = cursoRepository;
        this.carreraRepository = carreraRepository;
        this.modelMapper = modelMapper;
        this.authorizationConfig = authorizationConfig;
        this.usuarioRepository = usuarioRepository;
        this.actividadRepository = actividadRepository;
    }


    public CursoResponseDto createCurso(Long carreraId,CursoRequestDto requestDto) {

        String userEmail = authorizationConfig.getCurrentUserEmail();

        if(userEmail == null){
            throw new UnauthorizeOperationException("No se encuentra AUTORIZADO!!");
        }

        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Carrera carrera = carreraRepository.findById(carreraId)
                .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));

        if (!usuario.getCarreras().contains(carrera)) {
            throw new UnauthorizeOperationException("No puedes actualizar. No está inscrito en la carrera.");
        }
          //esta comprobacion inpide asignar el mismo  curso para varias carreras
//        if (cursoRepository.findByNombre(requestDto.getNombre()).isPresent()) {
//            throw new ResourceConflictException("Curso ya existente");
//        }


        Curso curso = new Curso();
        modelMapper.map(requestDto, curso);

        curso.setCarrera(carrera);

        cursoRepository.save(curso);
        return modelMapper.map(curso, CursoResponseDto.class);
    }

    public Curso AsignarCurso(CursoRequestAsignarDto requestDto) {

        String userEmail = authorizationConfig.getCurrentUserEmail();

        if(userEmail == null){
            throw new UnauthorizeOperationException("No se encuentra AUTORIZADO!!");
        }

        Carrera carrera = carreraRepository.findById(requestDto.getCarreraId())
                .orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrada"));

        //esta comprobacion inpide asignar el mismo  curso para varias carreras
//        if (cursoRepository.findByNombre(requestDto.getNombre()).isPresent()) {
//            throw new ResourceConflictException("Curso ya existente");
//        }


//
//        modelMapper.map(requestDto, curso);
        Curso curso = cursoRepository.findById(requestDto.getCursoId()).orElseThrow(() -> new ResourceNotFoundException("curso no encontrada"));
         List<Curso> temp =carrera.getCursos();
         temp.add(curso);
        carrera.setCursos(temp);
        carreraRepository.save(carrera);
        return curso;
//        return modelMapper.map(curso, CursoResponseDto.class);
    }

    public List<ActividadResponseDto> getActividadCursoByNameOrId(String  nombre_id){
        Curso temp;
        if(nombre_id.length() < 3 ){
            temp = cursoRepository.findById(Long.valueOf(nombre_id)).orElseThrow(() -> new ResourceNotFoundException("curso no encontrada"));
        }else{
             temp = cursoRepository.findByNombre(nombre_id).orElseThrow(() -> new ResourceNotFoundException("curso no encontrada"));
        }
        List<Actividad> new_temp =  temp.getActividades();
        List<ActividadResponseDto> ActividadResponseDtos = new ArrayList<>();
        for ( Actividad act: new_temp) {
            ActividadResponseDtos.add(modelMapper.map(act, ActividadResponseDto.class));
        }
        return ActividadResponseDtos;

    }

    public Page<CursoResponseDto> retornarByCarrera(Pageable pageable){
        return cursoRepository.findAll(pageable)
                .map(curso -> modelMapper.map(curso, CursoResponseDto.class));
    }

    public CursoResponseDto getCursoById(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));
        return modelMapper.map(curso, CursoResponseDto.class);
    }

    public CursoResponseDto updateCurso(Long id, CursoRequestDto requestDto) {

        String userEmail = authorizationConfig.getCurrentUserEmail();

        if(userEmail == null){
            throw new UnauthorizeOperationException("No se encuentra AUTORIZADO!!");
        }

        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        if (!usuario.getCarreras().contains(curso.getCarrera())) {
            throw new UnauthorizeOperationException("No puedes actualizar. No está inscrito en la carrera.");
        }

        curso.setNombre(requestDto.getNombre());
        cursoRepository.save(curso);


        return modelMapper.map(curso, CursoResponseDto.class);
    }

    public void deleteCurso(Long id) {

        String userEmail = authorizationConfig.getCurrentUserEmail();

        if(userEmail == null){
            throw new UnauthorizeOperationException("No se encuentra AUTORIZADO!!");
        }

        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        if (!usuario.getCarreras().contains(curso.getCarrera())) {
            throw new UnauthorizeOperationException("No puedes Eliminar. No está inscrito en la carrera.");
        }

        cursoRepository.delete(curso);
    }
}

