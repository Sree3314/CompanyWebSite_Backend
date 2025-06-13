package com.example.MainProject.dto;
 
import java.time.LocalDateTime;

import java.util.List;
 
public class QuestionResponse {

    private Long id;

    private String title;

    private String content;

    private Long userId;

    private String userName; // e.g., "Angela Martin"

    private String userRole; // e.g., "HR"

    private String userProfilePictureUrl; // Optional, if you display it

    private LocalDateTime postedAt;

    private LocalDateTime lastUpdatedAt;

    private List<AnswerResponse> answers;
 
    // Constructors, Getters and Setters

    public QuestionResponse() {}
 
    public QuestionResponse(Long id, String title, String content, Long userId, String userName, String userRole, String userProfilePictureUrl, LocalDateTime postedAt, LocalDateTime lastUpdatedAt, List<AnswerResponse> answers) {

        this.id = id;

        this.title = title;

        this.content = content;

        this.userId = userId;

        this.userName = userName;

        this.userRole = userRole;

        this.userProfilePictureUrl = userProfilePictureUrl;

        this.postedAt = postedAt;

        this.lastUpdatedAt = lastUpdatedAt;

        this.answers = answers;

    }
 
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getUserRole() { return userRole; }

    public void setUserRole(String userRole) { this.userRole = userRole; }

    public String getUserProfilePictureUrl() { return userProfilePictureUrl; }

    public void setUserProfilePictureUrl(String userProfilePictureUrl) { this.userProfilePictureUrl = userProfilePictureUrl; }

    public LocalDateTime getPostedAt() { return postedAt; }

    public void setPostedAt(LocalDateTime postedAt) { this.postedAt = postedAt; }

    public LocalDateTime getLastUpdatedAt() { return lastUpdatedAt; }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) { this.lastUpdatedAt = lastUpdatedAt; }

    public List<AnswerResponse> getAnswers() { return answers; }

    public void setAnswers(List<AnswerResponse> answers) { this.answers = answers; }

}
 