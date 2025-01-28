package com.mdci.backend.service;

import com.mdci.backend.config.JwtUtils;
import com.mdci.backend.dto.AuthRequest;
import com.mdci.backend.dto.AuthResponse;
import com.mdci.backend.dto.UserDTO;
import com.mdci.backend.exceptions.ValidationException;
import com.mdci.backend.model.Role;
import com.mdci.backend.model.User;
import com.mdci.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ValidationException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ValidationException("Invalid username or password");
        }

        String token = jwtUtils.generateToken(user.getUsername(), user.getRoles());
        return new AuthResponse(token);
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(userDTO.getRoles().stream().map(Role::valueOf).collect(Collectors.toSet()));
        user = userRepository.save(user);

        UserDTO result = new UserDTO();
        result.setUsername(user.getUsername());
        result.setRoles(user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()));
        return result;
    }
}