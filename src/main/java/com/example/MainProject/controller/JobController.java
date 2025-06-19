package com.example.MainProject.controller;

import com.example.MainProject.dto.JobDTO;
import com.example.MainProject.model.Job;
import com.example.MainProject.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; // NEW: Import for validation
import org.springframework.web.bind.MethodArgumentNotValidException; // NEW: Import for exception handling

import java.util.HashMap; // NEW: Import for error handling
import java.util.List;
import java.util.Map; // NEW: Import for error handling
import java.util.stream.Collectors; // Potentially needed if you stream, but not directly for this handler

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    // MODIFIED: Added @Valid to enable validation on JobDTO
    public ResponseEntity<Job> postJob(@Valid @RequestBody JobDTO jobDTO) {
        Job job = jobService.postJob(jobDTO);
        return ResponseEntity.ok(job);
    }

    // NEW: Exception handler to return detailed validation errors
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<Job>> getJobsByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(jobService.getJobsByManager(managerId));
    }

    @PreAuthorize("hasRole('MANAGER')") // Only managers can delete jobs
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        boolean deleted = jobService.deleteJob(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content for successful deletion
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found if job does not exist
        }
    }
}