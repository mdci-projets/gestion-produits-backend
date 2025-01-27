package com.mdci.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Supprimer le préfixe "Bearer "

            try {
                // Vérifier la validité et l'expiration du token
                if (jwtUtils.isTokenValid(token)) {
                    String username = jwtUtils.extractUsername(token);
                    Set<String> roles = jwtUtils.extractRoles(token);

                    // Convertir les rôles en SimpleGrantedAuthority
                    var authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toSet());

                    // Configurer le contexte de sécurité
                    var authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    // Token invalide ou expiré
                    throw new RuntimeException("Token is invalid or expired");
                }
            } catch (Exception ex) {
                // En cas d'erreur (invalide ou expiré), envoyer une réponse 401 Unauthorized
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
                response.setContentType("application/json");
                return;
            }
        }

        // Continuer la chaîne de filtres
        filterChain.doFilter(request, response);
    }
}