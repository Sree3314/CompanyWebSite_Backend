package com.example.MainProject.controller;
 
import com.example.MainProject.dto.JobDTO;

import com.example.MainProject.model.Job;

import com.example.MainProject.service.JobService;
 
import jakarta.validation.Valid;
 
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

}

 