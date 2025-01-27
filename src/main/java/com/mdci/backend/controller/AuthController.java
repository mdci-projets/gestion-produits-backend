package com.mdci.backend.controller;

import com.mdci.backend.dto.AuthRequest;
import com.mdci.backend.dto.AuthResponse;
import com.mdci.backend.dto.UserDTO;
import com.mdci.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody UserDTO userDTO) {
        return authService.register(userDTO);
    }
}
