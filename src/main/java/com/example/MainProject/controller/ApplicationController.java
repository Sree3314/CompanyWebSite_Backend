package com.example.MainProject.controller;
 
import com.example.MainProject.dto.ApplicationDTO;
import com.example.MainProject.model.Application;
import com.example.MainProject.model.Application.Status;
import com.example.MainProject.model.User; // Import your User model
import com.example.MainProject.service.ApplicationService;
import com.example.MainProject.repository.UserRepository; // To fetch the authenticated user's details
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication; // Needed to get the authenticated principal
import org.springframework.web.bind.annotation.*;
 
import java.security.Principal; // Keep this for applyForJob if still used for getting email
import java.util.List;
 
@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
 
	private final ApplicationService applicationService;
	private final UserRepository userRepository; // Inject UserRepository to find user by email/principal name
 
	public ApplicationController(ApplicationService applicationService, UserRepository userRepository) {
		this.applicationService = applicationService;
		this.userRepository = userRepository;
	}
 
	@PreAuthorize("hasRole('USER')")
	@PostMapping
	public ResponseEntity<Application> applyForJob(@RequestBody ApplicationDTO dto, Principal principal) {
		return ResponseEntity.ok(applicationService.applyForJob(dto, principal));
	}
 
	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/job/{jobId}")
	public ResponseEntity<List<Application>> getApplicationsByJob(@PathVariable Long jobId) {
		return ResponseEntity.ok(applicationService.getApplicationsByJob(jobId));
	}
 
	/**
	 * UPDATED METHOD: Retrieves applications for a specific employee.
	 * Securely ensures that a USER can only view their own applications.
	 * A manager, however, can view any employee's applications.
	 *
	 * @param employeeId The ID of the employee whose applications are being requested.
	 * @param authentication The Spring Security Authentication object representing the current user.
	 * @return A list of applications for the specified employee, or 403 Forbidden if unauthorized.
	 */
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER')") // Allow both USER and MANAGER to hit this endpoint
	@GetMapping("/employee/{employeeId}")
	public ResponseEntity<List<Application>> getApplicationsByEmployee(
			@PathVariable Long employeeId,
			Authentication authentication) {
 
		// Get the email (username) of the currently authenticated user from the Authentication object
		String authenticatedUserEmail = authentication.getName();
 
		// Retrieve the User entity for the authenticated user from the database
		User authenticatedUser = userRepository.findByEmail(authenticatedUserEmail)
				.orElseThrow(() -> new RuntimeException("Authenticated user not found in database: " + authenticatedUserEmail));
 
		// Role-based authorization and ownership check
		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
			// If the authenticated user is a 'USER', they can only view their own applications
			if (!authenticatedUser.getEmployeeId().equals(employeeId)) {
				// If the requested employeeId does not match the authenticated user's ID, deny access
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"))) {
			// If the authenticated user is a 'MANAGER', they are allowed to view any employee's applications.
			// No further ID check is needed here for managers.
		} else {
			// If the user has neither USER nor MANAGER role (should be caught by @PreAuthorize if configured well)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
 
		// If all checks pass, proceed to get the applications from the service
		return ResponseEntity.ok(applicationService.getApplicationsByEmployee(employeeId));
	}
 
	@PreAuthorize("hasRole('MANAGER')")
	@PutMapping("/{id}/status")
	public ResponseEntity<Application> updateApplicationStatus(@PathVariable Long id, @RequestParam Status status) {
		return ResponseEntity.ok(applicationService.updateApplicationStatus(id, status));
	}
 
	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping
	public ResponseEntity<List<Application>> getAllApplications() {
		List<Application> applications = applicationService.findAllApplications();
		return ResponseEntity.ok(applications);
	}
}
 
 