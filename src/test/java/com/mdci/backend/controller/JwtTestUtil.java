package com.mdci.backend.controller;

import com.mdci.backend.config.JwtUtils;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class JwtTestUtil {

    private final JwtUtils jwtUtils;

    public JwtTestUtil(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public String generateAdminToken() {
        return jwtUtils.generateToken("adminUser", Set.of("ADMIN"));
    }

    public String generateUserToken() {
        return jwtUtils.generateToken("user", Set.of("USER"));
    }
}
