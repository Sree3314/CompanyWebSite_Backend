package com.example.MainProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan; // Import EntityScan

/**
 * Main Spring Boot application class for the Company Hub Auth Module.
 * This module focuses solely on user registration, login, and profile management.
 */
@SpringBootApplication
@EntityScan(basePackages = "com.example.MainProject.model") // Scan for User entity
public class MainProjectApplication {

     public static void main(String[] args) {
        SpringApplication.run(MainProjectApplication.class, args);
    }

}
