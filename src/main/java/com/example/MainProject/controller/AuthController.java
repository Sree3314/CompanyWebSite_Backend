package com.example.MainProject.controller;

import com.example.MainProject.dto.AuthResponse;
import com.example.MainProject.dto.LoginRequest;
import com.example.MainProject.dto.RegisterRequest;
import com.example.MainProject.model.User;
import com.example.MainProject.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for authentication operations (register, login).
 * Password reset and OTP verification endpoints are removed for this simplified phase.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Handles user registration. Expects a pre-approved employee ID in the DB.
     * Upon successful registration, the account status changes directly to ACTIVE.
     * POST /api/auth/register
     * @param request RegisterRequest DTO.
     * @return ResponseEntity with success message or error.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        try {
            User registeredUser = authService.registerUser(request);
            return ResponseEntity.status(HttpStatus.OK).body("Registration successful. Account is now active.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Authenticates a user and returns a JWT.
     * POST /api/auth/login
     * @param loginRequest LoginRequest DTO.
     * @return ResponseEntity with AuthResponse (JWT, email, roles) or error.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = authService.authenticateUser(loginRequest);
            return ResponseEntity.ok(authResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
