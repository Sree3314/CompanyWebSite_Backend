// ApplicationController.java

package com.example.MainProject.controller;
 
import com.example.MainProject.dto.ApplicationDTO;

import com.example.MainProject.model.Application;

import com.example.MainProject.model.Application.Status;

import com.example.MainProject.service.ApplicationService;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
 
import java.security.Principal;

import java.util.List;
 
@RestController

@RequestMapping("/api/applications")


public class ApplicationController {
 
	private final ApplicationService applicationService;
 
	public ApplicationController(ApplicationService applicationService) {

		this.applicationService = applicationService;

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
 
	@PreAuthorize("hasRole('USER')")

	@GetMapping("/employee/{employeeId}")

	public ResponseEntity<List<Application>> getApplicationsByEmployee(@PathVariable Long employeeId) {

		return ResponseEntity.ok(applicationService.getApplicationsByEmployee(employeeId));

	}
 
	@PreAuthorize("hasRole('MANAGER')")

	@PutMapping("/{id}/status")

	public ResponseEntity<Application> updateApplicationStatus(@PathVariable Long id, @RequestParam Status status) {

		return ResponseEntity.ok(applicationService.updateApplicationStatus(id, status));

	}

}
 