package com.example.MainProject.service;

import com.example.MainProject.model.User; // Assuming the applicant is a User
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Service for sending various types of emails within the application.
 */
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends an email to an applicant whose application has been accepted.
     * @param applicant The User object representing the applicant.
     */
    public void sendApplicationAcceptedEmail(User applicant) {
        // Basic check for personal email presence
        if (applicant.getPersonalEmail() == null || applicant.getPersonalEmail().trim().isEmpty()) {
            System.err.println("Skipping accepted email: Applicant " + applicant.getEmployeeId() + " has no personal email specified.");
            return;
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(applicant.getPersonalEmail());
            helper.setSubject("Congratulations! Your Application Status Update - CompanyHub");
            helper.setText(
                "Dear " + applicant.getFirstName() + ",\n\n" +
                "Congratulations on moving forward to the next round! Your application has been accepted and we will get back to you with the interview schedule.\n\n" +
                "Good luck, all the best.\n\n" +
                "Team CompanyHub",
                false // false for plain text, true for HTML (if you want to use HTML later)
            );
            mailSender.send(message);
            System.out.println("Sent accepted email to: " + applicant.getPersonalEmail());
        } catch (MessagingException e) {
            System.err.println("Error sending accepted email to " + applicant.getPersonalEmail() + ": " + e.getMessage());
            // In a real application, you'd use a proper logger here (e.g., org.slf4j.Logger)
        }
    }

    /**
     * Sends an email to an applicant whose application has been rejected.
     * @param applicant The User object representing the applicant.
     */
    public void sendApplicationRejectedEmail(User applicant) {
        // Basic check for personal email presence
        if (applicant.getPersonalEmail() == null || applicant.getPersonalEmail().trim().isEmpty()) {
            System.err.println("Skipping rejected email: Applicant " + applicant.getEmployeeId() + " has no personal email specified.");
            return;
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(applicant.getPersonalEmail());
            helper.setSubject("Update on Your Application - CompanyHub");
            helper.setText(
                "Dear " + applicant.getFirstName() + ",\n\n" +
                "Thank you for your interest in CompanyHub.\n\n" +
                "We regret to inform you that your application has not been successful at this time. We wish you the best in your job search.\n\n" +
                "Good luck next time!\n\n" +
                "Team CompanyHub",
                false // false for plain text, true for HTML
            );
            mailSender.send(message);
            System.out.println("Sent rejected email to: " + applicant.getPersonalEmail());
        } catch (MessagingException e) {
            System.err.println("Error sending rejected email to " + applicant.getPersonalEmail() + ": " + e.getMessage());
            // In a real application, you'd use a proper logger here
        }
    }
}