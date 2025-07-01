package com.example.MainProject.service;

import com.example.MainProject.dto.AuthResponse;
import com.example.MainProject.dto.LoginRequest;
import com.example.MainProject.dto.RegisterRequest;
import com.example.MainProject.model.User;
import com.example.MainProject.model.User.AccountStatus;
import com.example.MainProject.model.User.Role;
import com.example.MainProject.model.VerificationToken;
import com.example.MainProject.repository.UserRepository;
import com.example.MainProject.repository.VerificationTokenRepository;
import com.example.MainProject.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailUsername; // Injected email username for 'From' address

    private static final Set<String> MANAGER_JOB_TITLES = new HashSet<>(Arrays.asList(
            "Software Engineering Manager", "Technical Lead", "Project Manager", "Product Manager",
            "Scrum Master", "Director of Engineering", "Engineering Manager",
            "Solutions Architect (with leadership focus)", "Release Manager", "IT Manager"
    ));

    private static final Set<String> EMPLOYEE_JOB_TITLES = new HashSet<>(Arrays.asList(
            "Software Developer (Backend)", "Frontend Engineer", "Mobile App Developer (Android)",
            "Quality Assurance (QA) Engineer", "DevOps Engineer", "Data Scientist",
            "UX Designer", "Full-Stack Developer", "Cloud Engineer", "Site Reliability Engineer (SRE)"
    ));

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService,
                       JwtUtil jwtUtil, VerificationTokenRepository verificationTokenRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailSender = mailSender;
    }

    /**
     * Registers a new user or allows an UNREGISTERED pre-approved employee to create an account.
     * Role is now determined by the provided job title.
     * @param request The RegisterRequest DTO.
     * @return The registered User entity.
     * @throws RuntimeException if employee ID not recognized, email mismatch, or account already registered/active.
     */
    @Transactional
    public User registerUser(RegisterRequest request) {
        // 1. Find the user by employee ID
        User user = userRepository.findByEmployeeId(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee ID not recognized. Cannot create an account."));

        // 2. Check if the account is already registered/active
        if (user.getAccountStatus() != User.AccountStatus.UNREGISTERED) {
            throw new RuntimeException("Account for this Employee ID is already registered or active.");
        }

        // 3. Validate ALL provided details against the existing UNREGISTERED record
        // This is the crucial part that needs to be enhanced
        if (!user.getEmail().equalsIgnoreCase(request.getEmail())) {
            throw new RuntimeException("Provided email does not match the pre-registered email for this employee ID.");
        }
        if (!user.getFirstName().equalsIgnoreCase(request.getFirstName())) {
            throw new RuntimeException("Provided first name does not match the pre-registered first name for this employee ID.");
        }
        if (!user.getLastName().equalsIgnoreCase(request.getLastName())) {
            throw new RuntimeException("Provided last name does not match the pre-registered last name for this employee ID.");
        }
        // Optional: Add checks for Department and Job Title if they are fixed and should not be changed by user during signup
        // If these fields are meant to be strictly pre-defined and unchangeable by the user, then add similar checks:
        if (user.getDepartment() != null && !user.getDepartment().equalsIgnoreCase(request.getDepartment())) {
             throw new RuntimeException("Provided department does not match the pre-registered department for this employee ID.");
        }
        if (user.getJobTitle() != null && !user.getJobTitle().equalsIgnoreCase(request.getJobTitle())) {
             throw new RuntimeException("Provided job title does not match the pre-registered job title for this employee ID.");
        }


        // 4. If all validations pass, determine role and update user details
        Role assignedRole = determineRoleByJobTitle(request.getJobTitle());
        user.setRole(assignedRole);

        // Update user details that are set during registration
        // NOTE: The first name, last name, email, department, job title are validated above,
        // so setting them here effectively re-sets them to the (validated) request values.
        // This is generally fine as long as they passed the comparison.
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setContactInformation(request.getContactInformation());
        user.setDepartment(request.getDepartment()); // Ensure this is set based on request, assuming validated above
        user.setJobTitle(request.getJobTitle());     // Ensure this is set based on request, assuming validated above
        user.setPersonalEmail(request.getPersonalEmail()); // This is a new field for the user to set
        user.setAccountStatus(AccountStatus.ACTIVE);

        User savedUser = userRepository.save(user);

        return savedUser;
    }

    /**
     * Helper method to determine the user's role based on their job title.
     * @param jobTitle The job title provided by the user.
     * @return User.Role (MANAGER or USER). Defaults to USER if not explicitly a manager title.
     */
    private Role determineRoleByJobTitle(String jobTitle) {
        if (jobTitle == null || jobTitle.trim().isEmpty()) {
            return Role.USER;
        }
        String normalizedJobTitle = jobTitle.trim();

        if (MANAGER_JOB_TITLES.contains(normalizedJobTitle)) {
            return Role.MANAGER;
        }
        return Role.USER;
    }

    // ... (rest of your AuthService methods like authenticateUser, initiatePasswordReset, resetPassword, sendEmail, generateNumericOtp) ...

    /**
     * Authenticates a user and generates a JWT.
     * UPDATED: Now includes the employeeId in the AuthResponse.
     * @param request LoginRequest DTO.
     * @return AuthResponse containing JWT, email, roles, and employeeId.
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
            throw new RuntimeException("Account is not active. Please ensure it's registered.");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        // Retrieve roles from UserDetails and convert to List<String>
        List<String> roles = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.toList());

        // ADDED: Fetch the User entity to get the employeeId
        User user = userRepository.findByEmail(request.getEmail())
                                 .orElseThrow(() -> new RuntimeException("User not found for email: " + request.getEmail()));

        // Return AuthResponse with JWT, email, roles, and the fetched employeeId
        return new AuthResponse(jwtUtil.generateToken(userDetails), userDetails.getUsername(), roles, user.getEmployeeId());
    }

    /**
     * Initiates the password reset process by sending an OTP to the user's personal email.
     * @param personalEmail The personal email address of the user for recovery.
     * @throws RuntimeException if user not found, personal email not set, or email sending fails.
     */
    @Transactional
    public void initiatePasswordReset(String personalEmail) {
        User user = userRepository.findByPersonalEmail(personalEmail)
                .orElseThrow(() -> new RuntimeException("User not found with personal email: " + personalEmail));

        String otp = generateNumericOtp(6);
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(15);

        verificationTokenRepository.deleteByUser(user);

        VerificationToken verificationToken = new VerificationToken(otp, user, expiryDate);
        verificationTokenRepository.save(verificationToken);

        String subject = "Company Hub - Password Reset OTP";
        String text = "Your OTP for password reset is: " + otp + "\nThis OTP is valid for 15 minutes.";
        sendEmail(personalEmail, subject, text);
    }

    /**
     * Resets the user's password after successful OTP verification.
     * @param organizationEmail The user's organization email.
     * @param otp The OTP received by the user.
     * @param newPassword The new password to set.
     * @throws RuntimeException if user not found, OTP invalid, or OTP expired.
     */
    @Transactional
    public void resetPassword(String organizationEmail, String otp, String newPassword) {
        // NOTE: The previous code was looking up by personalEmail here.
        // If the reset form requests the organizationEmail, then findByEmail should be used.
        // Assuming 'organizationEmail' parameter means the primary email in your User model.
        User user = userRepository.findByEmail(organizationEmail) // Changed from findByPersonalEmail
                .orElseThrow(() -> new RuntimeException("User not found with organization email: " + organizationEmail));

        VerificationToken verificationToken = verificationTokenRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("No active password reset request found for this user."));

        if (!verificationToken.getToken().equals(otp)) {
            throw new RuntimeException("Invalid OTP.");
        }

        if (verificationToken.isExpired()) {
            verificationTokenRepository.delete(verificationToken);
            throw new RuntimeException("OTP has expired. Please request a new one.");
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);
    }

    /**
     * Helper method to send an email.
     * @param to The recipient's email address.
     * @param subject The subject of the email.
     * @param text The body text of the email.
     */
    private void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(this.mailUsername);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + to + ": " + e.getMessage());
            throw new RuntimeException("Failed to send email. Please check server configuration or contact support. Error: " + e.getMessage());
        }
    }

    /**
     * Helper method to generate a numeric OTP of specified length.
     * @param length The desired length of the OTP.
     * @return A randomly generated numeric OTP string.
     */
    private String generateNumericOtp(int length) {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}