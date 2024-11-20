package com.example.proyecto.auth.domain;

import com.example.proyecto.exception.UserAlreadyExistException;
import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.infrastructure.UsuarioRepository;
import com.example.proyecto.auth.dto.JwtTokenToUse;
import com.example.proyecto.auth.dto.DatosNecesariosIniciarSesion;
import com.example.proyecto.auth.dto.DatosNecesariosRegistro;
import com.example.proyecto.UtilConfig.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final ModelMapper modelMapper;
    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UsuarioRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
    }

    public JwtTokenToUse realizar_inicio_sesion(DatosNecesariosIniciarSesion req) {
        Optional<Usuario> user = userRepository.findByEmail(req.getEmail());

        if (user.isEmpty()) throw new UsernameNotFoundException("No Email Found");
        if (!passwordEncoder.matches(req.getPassword(), user.get().getPassword()))
            throw new IllegalArgumentException("Password Is Wrong");

        JwtTokenToUse temp = new JwtTokenToUse();
        temp.setToken(jwtService.generarToken(user.get()));
        return temp;
    }

    public JwtTokenToUse realizar_registro(DatosNecesariosRegistro req) {
        Optional<Usuario> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) throw new UserAlreadyExistException("Email is Already Used");

        Usuario usuario = new Usuario();
        usuario.setNombre(req.getNombre());
        usuario.setEmail(req.getEmail());
        usuario.setFechaRegistro(req.getFechaRegistro());
        usuario.setPassword(passwordEncoder.encode(req.getPassword()));

        userRepository.save(usuario);
        JwtTokenToUse temp = new JwtTokenToUse();
        temp.setToken(jwtService.generarToken(usuario));
        return temp;
    }

}