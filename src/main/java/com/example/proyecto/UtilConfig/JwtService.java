package com.example.proyecto.UtilConfig;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.proyecto.usuario.domail.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private final UsuarioService usuarioService;
    public JwtService(UsuarioService usuarioService ){
        this.usuarioService = usuarioService;
    }

    public String generarToken(UserDetails data){
        var now = new Date();
        var now1 = new Date().getTime();
        //expr = 9 horas
        //var hour = 1000 * 60 * 60;
        //var day = 1000 * 60 * 60 * 24;
        Date expiration = new Date(now1 + (1000 * 60 * 60 * 9));

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(data.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(algorithm);
    }
    public String extractUsername(String token) {
        return JWT.decode(token).getSubject();
    }

    public void validateToken(String token, String userEmail) throws AuthenticationException {
        JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        UserDetails user = usuarioService.findByEmail(userEmail);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
    }

}
