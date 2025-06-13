package com.example.MainProject.service;

import com.example.MainProject.dto.JobDTO;
import com.example.MainProject.model.Job;
import com.example.MainProject.model.User;
import com.example.MainProject.repository.JobRepository;
import com.example.MainProject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobService(JobRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    public Job postJob(JobDTO jobDTO) {
        Job job = new Job();
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setLocation(jobDTO.getLocation());
        job.setSalary(jobDTO.getSalary());
        job.setJobType(jobDTO.getJobType());
        job.setExperienceLevel(jobDTO.getExperienceLevel());
        job.setSkillsRequired(jobDTO.getSkillsRequired());
        job.setPostedDate(new Date());

        User manager = userRepository.findById(jobDTO.getManagerId())
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        job.setManager(manager);

        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public List<Job> getJobsByManager(Long managerId) {
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        return jobRepository.findByManager(manager);
    }
}
