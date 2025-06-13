package com.example.MainProject.dto;
 
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
 
public class QuestionRequest {

    @NotBlank(message = "Title is required")

    @Size(max = 255, message = "Title cannot exceed 255 characters")

    private String title;
 
    @NotBlank(message = "Content is required")

    private String content;
 
    // Getters and Setters

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

}
 