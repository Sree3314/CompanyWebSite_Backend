

package com.example.MainProject.controller;
 
import com.example.MainProject.dto.AnswerRequest;

import com.example.MainProject.dto.QuestionRequest;

import com.example.MainProject.dto.QuestionResponse;

import com.example.MainProject.dto.AnswerResponse; // Assuming you'd want to return this type

import com.example.MainProject.model.User;

import com.example.MainProject.service.FaqService;

import com.example.MainProject.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.messaging.handler.annotation.DestinationVariable;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
 
@Controller // Use @Controller for WebSocket message handling

public class FaqMessageController {
 
    private static final Logger logger = LoggerFactory.getLogger(FaqMessageController.class);
 
    private final FaqService faqService;

    private final UserService userService;

    // No need to inject SimpMessagingTemplate here if FaqService already broadcasts.

    // If you want this controller to also broadcast directly, you'd inject it.
 
    @Autowired

    public FaqMessageController(FaqService faqService, UserService userService) {

        this.faqService = faqService;

        this.userService = userService;

    }
 
    // Helper method to get the authenticated user's ID for WebSocket context

    // This is similar to the one in FaqController but adapted for WebSocket's SecurityContext

    private Long getAuthenticatedUserId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {

            logger.warn("Authentication is null, not authenticated, or anonymous in WebSocket context.");

            throw new RuntimeException("User not authenticated for WebSocket operation.");

        }
 
        Object principal = authentication.getPrincipal();

        Long userId = null;
 
        if (principal instanceof UserDetails userDetails) {

            String username = userDetails.getUsername(); // This should be the email

            User user = userService.getUserRepository().findByEmail(username)

                    .orElseThrow(() -> new RuntimeException("Authenticated user not found in DB with email: " + username));

            userId = user.getEmployeeId();

        } else if (principal instanceof String email) {

            User user = userService.getUserRepository().findByEmail(email)

                    .orElseThrow(() -> new RuntimeException("Authenticated user not found in DB with email: " + email));

            userId = user.getEmployeeId();

        } else {

            logger.error("Unexpected principal type for WebSocket: {}. Cannot extract user ID.", principal.getClass().getName());

            throw new RuntimeException("Could not determine authenticated user ID for WebSocket operation.");

        }

        return userId;

    }
 
    /**

     * Handles messages sent to /app/faq/postQuestion

     * Client sends: STOMP.send("/app/faq/postQuestion", {}, JSON.stringify({ title: "...", content: "..." }));

     * This method will then call the service and potentially broadcast the new question.

     */

    @MessageMapping("/faq/postQuestion")

    public void handlePostQuestion(@Payload QuestionRequest request) {

        try {

            Long userId = getAuthenticatedUserId();

            // Call the service to post the question. FaqService will handle saving and potentially broadcasting.

            QuestionResponse newQuestion = faqService.postQuestion(request, userId);

            logger.info("New question posted via WebSocket STOMP: {}", newQuestion.getId());

            // If you want to broadcast new questions to /topic/faq/questions (not just answers)

            // you'd add this to FaqService's postQuestion or here if FaqService doesn't do it.

            // For now, only answers are broadcast from FaqService.

        } catch (Exception e) {

            logger.error("Error posting question via WebSocket STOMP: {}", e.getMessage(), e);

            // In a real application, you might send an error message back to the client

            // using SimpMessagingTemplate to a user-specific queue.

        }

    }
 
    /**

     * Handles messages sent to /app/faq/questions/{questionId}/postAnswer

     * Client sends: STOMP.send("/app/faq/questions/123/postAnswer", {}, JSON.stringify({ content: "..." }));

     * This method will call the service, and the service will broadcast the answer.

     */

    @MessageMapping("/faq/questions/{questionId}/postAnswer")

    public void handlePostAnswer(@DestinationVariable Long questionId, @Payload AnswerRequest request) {

        try {

            Long userId = getAuthenticatedUserId();

            // Call the service to post the answer. FaqService will handle saving and broadcasting.

            AnswerResponse newAnswer = faqService.postAnswer(questionId, request, userId);

            logger.info("New answer posted for question {} via WebSocket STOMP: {}", questionId, newAnswer.getId());

        } catch (Exception e) {

            logger.error("Error posting answer via WebSocket STOMP for question ID {}: {}", questionId, e.getMessage(), e);

            // Optionally, send an error back to the client.

        }

    }
 
    // You could add other @MessageMapping methods here for other real-time interactions,

    // e.g., for live editing, status updates, etc.

}
 