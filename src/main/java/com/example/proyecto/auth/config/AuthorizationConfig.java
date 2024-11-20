package com.example.proyecto.auth.config;

import com.example.proyecto.material.domail.Material;
import com.example.proyecto.material.domail.MaterialService;
import com.example.proyecto.material.infrastructure.MaterialRepository;
import com.example.proyecto.usuario.domail.Usuario;
import com.example.proyecto.usuario.domail.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationConfig {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MaterialRepository materialRepository;


    public boolean esPropietario(Long usuarioId, Long materialId) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("Material no encontrado"));
        return material.getPropietario().getId().equals(usuarioId);
    }


    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }catch (ClassCastException no_found){
            return null;
        }
    }

}
