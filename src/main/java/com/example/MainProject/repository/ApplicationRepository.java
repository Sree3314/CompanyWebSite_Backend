package com.example.MainProject.repository;

import com.example.MainProject.model.Application;
import com.example.MainProject.model.Job;
import com.example.MainProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByJob(Job job);
    List<Application> findByEmployee(User employee);
}
