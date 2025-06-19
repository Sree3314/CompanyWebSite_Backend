package com.example.MainProject.dto;
 
import jakarta.validation.constraints.Min;     // For numeric minimum values
import jakarta.validation.constraints.NotBlank; // For String fields that cannot be null or empty/whitespace
import jakarta.validation.constraints.NotNull;  // For fields that cannot be null
 
public class ApplicationDTO {
 
    @NotNull(message = "Job ID cannot be null") // Ensures jobId is provided
    private Long jobId;
 
    @NotBlank(message = "Resume link cannot be empty") // Ensures resumeLink is not null and contains non-whitespace characters
    private String resumeLink;
 
    @NotBlank(message = "Skills cannot be empty") // Ensures skills is not null and contains non-whitespace characters
    private String skills;
 
    @Min(value = 0, message = "Years of experience cannot be negative") // Ensures yearsOfExperience is not less than 0
    private int yearsOfExperience;
 
    // Getters and Setters (unchanged from your original code)
    public Long getJobId() {
        return jobId;
    }
 
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
 
    public String getResumeLink() {
        return resumeLink;
    }
 
    public void setResumeLink(String resumeLink) {
        this.resumeLink = resumeLink;
    }
 
    public String getSkills() {
        return skills;
    }
 
    public void setSkills(String skills) {
        this.skills = skills;
    }
 
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }
 
    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}
 
 