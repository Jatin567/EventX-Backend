package com.event.eventx.service;

import com.event.eventx.dto.LoginRequest;
import com.event.eventx.dto.RegisterRequest;
import com.event.eventx.dto.JwtResponse;
import com.event.eventx.dto.MessageResponse;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
    MessageResponse registerUser(RegisterRequest registerRequest);
}
