package com.example.MainProject.service;
 
import com.example.MainProject.dto.ApplicationDTO;
import com.example.MainProject.model.Application;
import com.example.MainProject.model.Application.Status;
import com.example.MainProject.model.Job;
import com.example.MainProject.model.User;
import com.example.MainProject.repository.ApplicationRepository;
import com.example.MainProject.repository.JobRepository;
import com.example.MainProject.repository.UserRepository;
import org.springframework.stereotype.Service;
 
import java.security.Principal;
import java.util.Date;
import java.util.List;
 
@Service
public class ApplicationService {
 
	private final ApplicationRepository applicationRepository;
	private final JobRepository jobRepository;
	private final UserRepository userRepository;
	private final EmailService emailService;
 
	public ApplicationService(ApplicationRepository applicationRepository, JobRepository jobRepository,
			UserRepository userRepository, EmailService emailService) {
		this.applicationRepository = applicationRepository;
		this.jobRepository = jobRepository;
		this.userRepository = userRepository;
		this.emailService = emailService; 
	}
 
	public Application applyForJob(ApplicationDTO dto, Principal principal) {
		Job job = jobRepository.findById(dto.getJobId()).orElseThrow(() -> new RuntimeException("Job not found"));
 
		User employee = userRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new RuntimeException("User not found"));
 
		Application application = new Application();
		application.setJob(job);
		application.setEmployee(employee);
		application.setResumeLink(dto.getResumeLink());
		application.setSkills(dto.getSkills());
		application.setYearsOfExperience(dto.getYearsOfExperience());
		application.setStatus(Status.PENDING);
		application.setAppliedDate(new Date());
 
// Set full name from User entity
		String fullName = employee.getFirstName() + " " + employee.getLastName();
		application.setEmployeeName(fullName);
 
		return applicationRepository.save(application);
	}
 
	public List<Application> getApplicationsByJob(Long jobId) {
		Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));
		return applicationRepository.findByJob(job);
	}
 
	public List<Application> getApplicationsByEmployee(Long employeeId) {
		User employee = userRepository.findById(employeeId)
				.orElseThrow(() -> new RuntimeException("Employee not found"));
		return applicationRepository.findByEmployee(employee);
	}
 
	public Application updateApplicationStatus(Long id, Status status) {
		Application application = applicationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Application not found"));
		User applicant = application.getEmployee();
		application.setStatus(status);
		 Application updatedApplication = applicationRepository.save(application);

	        // --- NEW: Trigger Email Based on New Status ---
	        if (applicant != null) { // Only attempt to send if an applicant is found
	            if (status == Status.ACCEPTED) { // Make sure 'Status.ACCEPTED' matches your enum value
	                emailService.sendApplicationAcceptedEmail(applicant);
	            } else if (status == Status.DECLINED) { // Make sure 'Status.REJECTED' matches your enum value
	                emailService.sendApplicationRejectedEmail(applicant);
	            }
	            // You can add more conditions here for other statuses if needed
	        }
	        // ------------------------------------------

	        return updatedApplication;
	}
}
 
 