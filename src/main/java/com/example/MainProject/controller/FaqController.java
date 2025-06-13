// src/main/java/com/example/MainProject/controller/FaqController.java (No changes needed for WebSocket integration)

package com.example.MainProject.controller;
 
import com.example.MainProject.dto.AnswerRequest;

import com.example.MainProject.dto.AnswerResponse;

import com.example.MainProject.dto.QuestionRequest;

import com.example.MainProject.dto.QuestionResponse;

import com.example.MainProject.model.User;

import com.example.MainProject.service.FaqService;

import com.example.MainProject.service.UserService;
 
import jakarta.validation.Valid;
 
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController

@RequestMapping("/api/faq")

public class FaqController {
 
    private static final Logger logger = LoggerFactory.getLogger(FaqController.class);
 
    private final FaqService faqService;

    private final UserService userService; // Assuming you have a UserService to get User details
 
    @Autowired

    public FaqController(FaqService faqService, UserService userService) {

        this.faqService = faqService;

        this.userService = userService;

    }
 
    // Helper method to get the authenticated user's ID

    private Long getAuthenticatedUserId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {

            throw new RuntimeException("User not authenticated.");

        }
 
        Object principal = authentication.getPrincipal();

        Long userId = null;
 
        if (principal instanceof UserDetails userDetails) {

            // Assuming the username in UserDetails is the user's email

            String username = userDetails.getUsername();

            User user = userService.getUserRepository().findByEmail(username)

                    .orElseThrow(() -> new RuntimeException("Authenticated user not found in DB with email: " + username));

            userId = user.getEmployeeId();

        } else if (principal instanceof String email) { // This might happen if JWT extracts directly

             User user = userService.getUserRepository().findByEmail(email)

                    .orElseThrow(() -> new RuntimeException("Authenticated user not found in DB with email: " + email));

            userId = user.getEmployeeId();

        } else {

            logger.warn("Authenticated principal is of unexpected type: {}", principal.getClass().getName());

            throw new RuntimeException("Could not determine authenticated user ID.");

        }

        return userId;

    }
 
    // Log user details for debugging (optional)

    private void logUserDetails() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {

            logger.info("No authenticated user.");

            return;

        }
 
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {

            if (userDetails instanceof User userEntity) { // If your User model implements UserDetails directly

                logger.info("Authenticated User (User entity):");

                logger.info("  ID: {}", userEntity.getEmployeeId());

                logger.info("  Email: {}", userEntity.getEmail());

                logger.info("  First Name: {}", userEntity.getFirstName());

                logger.info("  Role: {}", userEntity.getRole());

            } else {

                logger.info("Authenticated UserDetails (not custom User entity):");

                logger.info("  Username: {}", userDetails.getUsername());

                logger.info("  Authorities: {}", userDetails.getAuthorities());

                logger.info("  Account Enabled: {}", userDetails.isEnabled());

            }

        } else if (principal instanceof String username) {

            logger.info("Authenticated User (String principal): {}", username);

            try {

                User user = userService.getUserRepository().findByEmail(username)

                        .orElse(null);

                if (user != null) {

                    logger.info("  Fetched User from DB for principal {}:", username);

                    logger.info("    ID: {}", user.getEmployeeId());

                    logger.info("    Email: {}", user.getEmail());

                    logger.info("    First Name: {}", user.getFirstName());

                    logger.info("    Role: {}", user.getRole());

                } else {

                    logger.warn("  Could not find user in DB for principal (email): {}", username);

                }

            } catch (Exception e) {

                logger.error("Error fetching user details from DB for principal {}: {}", username, e.getMessage());

            }

        } else {

            logger.warn("Authenticated principal is of unexpected type: {}", principal.getClass().getName());

        }

    }
 
    // --- Question Endpoints ---
 
    @PostMapping("/questions")

    public ResponseEntity<QuestionResponse> postQuestion(@Valid @RequestBody QuestionRequest questionRequest) {

        logUserDetails();

        Long userId = getAuthenticatedUserId();

        QuestionResponse newQuestion = faqService.postQuestion(questionRequest, userId);

        return new ResponseEntity<>(newQuestion, HttpStatus.CREATED);

    }
 
    @GetMapping("/questions")

    public ResponseEntity<List<QuestionResponse>> getAllQuestions() {

        logUserDetails();

        List<QuestionResponse> questions = faqService.getAllQuestions();

        return ResponseEntity.ok(questions);

    }
 
    @GetMapping("/questions/{questionId}")

    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long questionId) {

        logUserDetails();

        QuestionResponse question = faqService.getQuestionById(questionId);

        return ResponseEntity.ok(question);

    }
 
    @PutMapping("/questions/{questionId}")

    public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable Long questionId, @Valid @RequestBody QuestionRequest questionRequest) {

        logUserDetails();

        Long userId = getAuthenticatedUserId();

        QuestionResponse updatedQuestion = faqService.updateQuestion(questionId, questionRequest, userId);

        return ResponseEntity.ok(updatedQuestion);

    }
 
    @DeleteMapping("/questions/{questionId}")

    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {

        logUserDetails();

        Long userId = getAuthenticatedUserId();

        faqService.deleteQuestion(questionId, userId);

        return ResponseEntity.noContent().build();

    }
 
    // --- Answer Endpoints ---
 
    @PostMapping("/questions/{questionId}/answers")

    public ResponseEntity<AnswerResponse> postAnswer(@PathVariable Long questionId, @Valid @RequestBody AnswerRequest answerRequest) {

        logUserDetails();

        Long userId = getAuthenticatedUserId();

        AnswerResponse newAnswer = faqService.postAnswer(questionId, answerRequest, userId);

        return new ResponseEntity<>(newAnswer, HttpStatus.CREATED);

    }
 
    @PutMapping("/answers/{answerId}")

    public ResponseEntity<AnswerResponse> updateAnswer(@PathVariable Long answerId, @Valid @RequestBody AnswerRequest answerRequest) {

        logUserDetails();

        Long userId = getAuthenticatedUserId();

        AnswerResponse updatedAnswer = faqService.updateAnswer(answerId, answerRequest, userId);

        return ResponseEntity.ok(updatedAnswer);

    }
 
    @DeleteMapping("/answers/{answerId}")

    public ResponseEntity<Void> deleteAnswer(@PathVariable Long answerId) {

        logUserDetails();

        Long userId = getAuthenticatedUserId();

        faqService.deleteAnswer(answerId, userId);

        return ResponseEntity.noContent().build();

    }

}
 