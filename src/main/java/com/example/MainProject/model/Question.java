package com.example.MainProject.model;
 
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
 
@Entity

@Table(name = "questions")

public class Question {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
 
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "user_id", nullable = false)

    private User user; // The user who posted the question
 
    @Column(nullable = false)

    private String title;
 
    @Column(nullable = false, columnDefinition = "TEXT")

    private String content;
 
    @Column(name = "posted_at", nullable = false)

    private LocalDateTime postedAt;
 
    @Column(name = "last_updated_at")

    private LocalDateTime lastUpdatedAt;
 
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)

    @OrderBy("postedAt ASC")

    private List<Answer> answers = new ArrayList<>();
 
    // Constructors

    public Question() {

        this.postedAt = LocalDateTime.now();

        this.lastUpdatedAt = LocalDateTime.now();

    }
 
    // Getters and Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public LocalDateTime getPostedAt() { return postedAt; }

    public void setPostedAt(LocalDateTime postedAt) { this.postedAt = postedAt; }

    public LocalDateTime getLastUpdatedAt() { return lastUpdatedAt; }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) { this.lastUpdatedAt = lastUpdatedAt; }

    public List<Answer> getAnswers() { return answers; }

    public void setAnswers(List<Answer> answers) { this.answers = answers; }
 
    @PreUpdate

    protected void onUpdate() {

        this.lastUpdatedAt = LocalDateTime.now();

    }

}
 