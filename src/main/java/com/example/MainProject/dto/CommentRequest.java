package com.example.MainProject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

/**
 * DTO for receiving a comment on an upload.
 */
public class CommentRequest {
    @NotBlank(message = "Comment cannot be empty")
    @Size(max = 1000, message = "Comment cannot exceed 1000 characters")
    private String comment;

    // No-argument constructor
    public CommentRequest() {
    }

    // All-argument constructor
    public CommentRequest(String comment) {
        this.comment = comment;
    }

    // Getter
    public String getComment() {
        return comment;
    }

    // Setter
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentRequest that = (CommentRequest) o;
        return Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comment);
    }

    @Override
    public String toString() {
        return "CommentRequest{" +
               "comment='" + comment + '\'' +
               '}';
    }
}
