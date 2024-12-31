package com.kardec.clinica.infra.security;

import com.kardec.clinica.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recupetarToken(request);

        if(tokenJWT != null){
            var subject = tokenService.getSubject(tokenJWT);
            var usuario = usuarioRepository.findByLogin(subject);

            var authenticantion = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticantion);
        }
        filterChain.doFilter(request, response);

    }

    private String recupetarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", "").trim();
        }

        return null;
    }
}
