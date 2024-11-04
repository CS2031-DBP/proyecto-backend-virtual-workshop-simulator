package com.example.proyecto.auth.config;

import com.example.proyecto.usuario.domail.Role;
import com.example.proyecto.usuario.domail.UsuarioService;
import com.example.proyecto.usuario.domail.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationConfig {

    @Autowired
    private UsuarioService usuarioService;

    public boolean verificar_si_es_admin(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();

        String name = userDetails.getUsername();
        String role = userDetails.getAuthorities().toArray()[0].toString();
        Usuario user= usuarioService.findByEmail(name, role);
        boolean es_verdad = user.getId().equals(id) || user.getRole().equals(Role.ADMIN);
        return es_verdad;
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
