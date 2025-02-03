package com.mdci.backend.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtils {

    // Clé secrète pour signer le token (utilisez un mécanisme sécurisé pour gérer cette clé en production)
    private final Key secretKey;

    // Durée de validité des tokens (par exemple, 1 heure)
    private final long tokenValidity = 3600000;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Permet d'injecter la clé secrète (utilisée dans les tests et l'application)
    public JwtUtils(@Value("${jwt.secret:mySecretKeymySecretKeymySecretKeymySecretKey}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Générer un token JWT
    public String generateToken(String username, Set<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)// Stocke les rôles comme une revendication
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
                .signWith(secretKey)
                .compact();
    }

    // Vérifier si le token est valide
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !isTokenExpired(claims);
        } catch (Exception e) {
            return false; // Token invalide ou mal formé
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token)
                .getSubject();
    }

    public Set<String> extractRoles(String token) {
        // Extraction brute de la revendication
        Object rolesClaim = extractAllClaims(token)
                .get("roles");

        // Conversion explicite de l'objet en Set<String> avec ObjectMapper
        return objectMapper.convertValue(rolesClaim, new TypeReference<Set<String>>() {
        });
    }

    // Vérifier si le token est expiré
    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    // Extraire toutes les réclamations (claims) du token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

