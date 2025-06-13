
 
package com.example.MainProject.model;
 
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
 
/**
 * JPA Entity for storing OTP (One-Time Password) for verification purposes (e.g., password reset).
 * It links to a User and has an expiry time.
 */
@Entity
@Table(name = "verification_token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(nullable = false, unique = true)
    private String token; // The actual OTP or verification string
 
    @OneToOne(fetch = FetchType.EAGER) // EAGER fetch for convenience, adjust if performance issue
    @JoinColumn(nullable = false, name = "user_id", unique = true) // One token per user at a time
    private User user;
 
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;
 
    // No-argument constructor (required by JPA)
    public VerificationToken() {
    }
 
    // Constructor with token and user
    public VerificationToken(String token, User user, LocalDateTime expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }
 
    // --- Getters ---
    public Long getId() {
        return id;
    }
 
    public String getToken() {
        return token;
    }
 
    public User getUser() {
        return user;
    }
 
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
 
    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }
 
    public void setToken(String token) {
        this.token = token;
    }
 
    public void setUser(User user) {
        this.user = user;
    }
 
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
 
    // --- Helper for expiry check ---
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
 
    // --- equals(), hashCode(), toString() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationToken that = (VerificationToken) o;
        return Objects.equals(id, that.id);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
 
    @Override
    public String toString() {
        return "VerificationToken{" +
               "id=" + id +
               ", token='" + token + '\'' +
               ", userId=" + (user != null ? user.getEmployeeId() : "null") + // Avoid circular dependency in toString
               ", expiryDate=" + expiryDate +
               '}';
    }
}
 
 