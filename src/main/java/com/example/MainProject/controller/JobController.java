package com.example.MainProject.controller;

import com.example.MainProject.dto.JobDTO;
import com.example.MainProject.model.Job;
import com.example.MainProject.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus; // Import HttpStatus for ResponseEntity
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<Job> postJob(@Valid @RequestBody JobDTO jobDTO) {
        Job job = jobService.postJob(jobDTO);
        return ResponseEntity.ok(job);
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

    // NEW: Endpoint to delete a job by ID
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