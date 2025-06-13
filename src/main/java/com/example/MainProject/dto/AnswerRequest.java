package com.example.MainProject.dto;
 
import jakarta.validation.constraints.NotBlank;
 
public class AnswerRequest {
    @NotBlank(message = "Content is required")
    private String content;
 
    // Getters and Setters
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
 