package com.event.eventx.service.impl;

import com.event.eventx.dto.LoginRequest;
import com.event.eventx.dto.RegisterRequest;
import com.event.eventx.dto.JwtResponse;
import com.event.eventx.dto.MessageResponse;
import com.event.eventx.entity.User;
import com.event.eventx.repository.UserRepository;
import com.event.eventx.security.JwtUtils;
import com.event.eventx.security.UserDetailsImpl;
import com.event.eventx.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired AuthenticationManager authenticationManager;
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder encoder;
    @Autowired JwtUtils jwtUtils;

    @Value("${admin.passcode}")
    private String adminPasscode;

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        return new JwtResponse(jwt, 
                               userDetails.getId(), 
                               userDetails.getName(), 
                               userDetails.getEmail(), 
                               role);
    }

    @Override
    public MessageResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new MessageResponse("Error: Email is already in use!");
        }

        // Determine role from passcode — frontend role field is ignored for security
        String submitted = registerRequest.getAdminPasscode();
        boolean isAdmin = submitted != null 
                          && submitted.trim().length() == 6 
                          && submitted.trim().equals(adminPasscode);

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole(isAdmin ? "ROLE_ADMIN" : "ROLE_USER");

        userRepository.save(user);
        return new MessageResponse(isAdmin 
            ? "Admin account created successfully!" 
            : "User registered successfully!");
    }
}
