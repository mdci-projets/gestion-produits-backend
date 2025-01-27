package com.mdci.backend.service;

import com.mdci.backend.dto.AuthRequest;
import com.mdci.backend.dto.AuthResponse;
import com.mdci.backend.dto.UserDTO;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    UserDTO register(UserDTO userDTO);
}
