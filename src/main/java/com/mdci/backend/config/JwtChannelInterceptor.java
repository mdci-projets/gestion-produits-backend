package com.mdci.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtUtils jwtUtils;

    public JwtChannelInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String authHeader = accessor.getFirstNativeHeader("Authorization");

        // 🔍 Vérifier si le token est dans les sessions WebSocket (récupéré par `WebSocketHandshakeInterceptor`)
        if (authHeader == null && accessor.getSessionAttributes() != null) {
            String token = (String) accessor.getSessionAttributes().get("token");

            if (token != null) {
                authHeader = "Bearer " + token;
            }
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Authentication auth = validateToken(token);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
                accessor.setUser(auth);
                log.info("✅ Utilisateur WebSocket authentifié: {}", auth.getName());
            } else {
                log.warn("❌ Token JWT invalide !");
            }
        } else {
            log.warn("❌ Aucun token JWT reçu !");
        }

        return message;
    }

    private Authentication validateToken(String token) {
        if (jwtUtils.isTokenValid(token)) {
            String username = jwtUtils.extractUsername(token);
            Set<String> roles = jwtUtils.extractRoles(token);

            if (username != null && !roles.isEmpty()) {
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();
                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            } else {
                log.warn("⚠️ Token JWT sans rôle ou utilisateur !");
            }
        } else {
            log.warn("❌ Token JWT invalide !");
        }
        return null;
    }
}
