package com.example.MainProject.model;
 
import jakarta.persistence.*;


import java.time.LocalDateTime;
 
@Entity

@Table(name = "answers")

public class Answer {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
 
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "question_id", nullable = false)

    private Question question; // The question this answer belongs to
 
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "user_id", nullable = false)

    private User user; // The user who posted the answer
 
    @Column(nullable = false, columnDefinition = "TEXT")

    private String content;
 
    @Column(name = "posted_at", nullable = false)

    private LocalDateTime postedAt;
 
    @Column(name = "last_updated_at")

    private LocalDateTime lastUpdatedAt;
 
    // Constructors

    public Answer() {

        this.postedAt = LocalDateTime.now();

        this.lastUpdatedAt = LocalDateTime.now();

    }
 
    // Getters and Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Question getQuestion() { return question; }

    public void setQuestion(Question question) { this.question = question; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public LocalDateTime getPostedAt() { return postedAt; }

    public void setPostedAt(LocalDateTime postedAt) { this.postedAt = postedAt; }

    public LocalDateTime getLastUpdatedAt() { return lastUpdatedAt; }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) { this.lastUpdatedAt = lastUpdatedAt; }
 
    @PreUpdate

    protected void onUpdate() {

        this.lastUpdatedAt = LocalDateTime.now();

    }

}
 