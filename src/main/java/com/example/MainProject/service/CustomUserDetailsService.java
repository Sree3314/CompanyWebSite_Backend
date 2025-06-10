package com.example.MainProject.service;

import com.example.MainProject.model.User;
import com.example.MainProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
// Removed unused imports if any from previous versions, keeping only necessary
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // In this NO-OTP phase, an account must be ACTIVE to be enabled for login.
        // UNREGISTERED accounts are not enabled.
        if (user.getAccountStatus() != User.AccountStatus.ACTIVE) {
            throw new org.springframework.security.authentication.DisabledException("User account is not active or not yet registered.");
        }

        // FIX: Return the User entity directly, as it implements UserDetails
        return user;
    }
}
