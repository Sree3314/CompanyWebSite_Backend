package com.example.MainProject.dto;
 
public class ApplicationDTO {
	private Long jobId;
	private String resumeLink;
	private String skills;
	private int yearsOfExperience;
 
	// Getters and Setters
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
 
 