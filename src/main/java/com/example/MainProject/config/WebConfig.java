package com.example.MainProject.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply CORS to all endpoints
                .allowedOrigins("http://localhost:4200") // Specify allowed origins (your frontend URLs)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specify allowed HTTP methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true) // Allow cookies, authorization headers, etc.
                .maxAge(3600); // Cache the preflight response for 1 hour
    }
}