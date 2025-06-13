package com.example.MainProject.dto;
 
import java.time.LocalDateTime;
 
public class AnswerResponse {
    private Long id;
    private Long questionId;
    private String content;
    private Long userId;
    private String userName; // e.g., "Ryan Howard"
    private String userRole; // e.g., "HR"
    private String userProfilePictureUrl; // Optional, if you display it
    private LocalDateTime postedAt;
    private LocalDateTime lastUpdatedAt;
 
    // Constructors, Getters and Setters
    public AnswerResponse() {}
 
    public AnswerResponse(Long id, Long questionId, String content, Long userId, String userName, String userRole, String userProfilePictureUrl, LocalDateTime postedAt, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.questionId = questionId;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;
        this.userProfilePictureUrl = userProfilePictureUrl;
        this.postedAt = postedAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }
 
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }
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
}
 