package com.example.MainProject.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

/**
 * DTO for receiving a rating for an upload.
 */
public class RatingRequest {
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot be more than 5")
    private Integer rating;

    // No-argument constructor
    public RatingRequest() {
    }

    // All-argument constructor
    public RatingRequest(Integer rating) {
        this.rating = rating;
    }

    // Getter
    public Integer getRating() {
        return rating;
    }

    // Setter
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingRequest that = (RatingRequest) o;
        return Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating);
    }

    @Override
    public String toString() {
        return "RatingRequest{" +
               "rating=" + rating +
               '}';
    }
}
