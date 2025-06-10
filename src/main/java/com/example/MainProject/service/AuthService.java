package com.example.MainProject.service;

import com.example.MainProject.dto.AuthResponse;
import com.example.MainProject.dto.LoginRequest;
import com.example.MainProject.dto.RegisterRequest;
import com.example.MainProject.model.User;
import com.example.MainProject.model.User.AccountStatus;
import com.example.MainProject.repository.UserRepository;
import com.example.MainProject.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Registers a new user or allows an UNREGISTERED pre-approved employee to create an account.
     * Expects the employeeId to exist in the database with status UNREGISTERED.
     * Upon successful registration, the account status changes directly to ACTIVE.
     * @param request The RegisterRequest DTO.
     * @return The registered User entity.
     * @throws RuntimeException if employee ID not recognized, email mismatch, or account already registered/active.
     */
    @Transactional
    public User registerUser(RegisterRequest request) {
        // Find if an UNREGISTERED user with this employeeId exists
        User user = userRepository.findByEmployeeId(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee ID not recognized. Cannot create an account."));

        // Check if the provided email matches the pre-registered employee's email
        if (!user.getEmail().equals(request.getEmail())) {
            throw new RuntimeException("Provided email does not match the pre-registered employee ID.");
        }

        // Check if the account is already registered or active
        if (user.getAccountStatus() != User.AccountStatus.UNREGISTERED) {
            throw new RuntimeException("Account for this Employee ID is already registered or active.");
        }

        // Update user details and set password
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setContactInformation(request.getContactInformation());
        user.setDepartment(request.getDepartment());
        user.setJobTitle(request.getJobTitle());
        user.setPersonalEmail(request.getPersonalEmail());
        user.setAccountStatus(AccountStatus.ACTIVE); // Directly set to ACTIVE as no OTP verification

        User savedUser = userRepository.save(user);

        return savedUser;
    }

    /**
     * Authenticates a user and generates a JWT.
     * @param request LoginRequest DTO.
     * @return AuthResponse containing JWT, email, and roles.
     * @throws RuntimeException if authentication fails or account is not active.
     */
    public AuthResponse authenticateUser(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Incorrect email or password.");
        } catch (DisabledException e) {
            // This means UserDetails.isEnabled() is false, i.e., account_status is not ACTIVE
            throw new RuntimeException("Account is not active. Please ensure it's registered.");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        // Retrieve roles from UserDetails and convert to Set<String>
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.toSet());

        return new AuthResponse(jwtUtil.generateToken(userDetails), userDetails.getUsername(), roles);
    }
}
