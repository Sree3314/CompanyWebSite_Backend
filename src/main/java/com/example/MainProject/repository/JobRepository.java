package com.example.MainProject.repository;

import com.example.MainProject.model.Job;
import com.example.MainProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByManager(User manager);
}
