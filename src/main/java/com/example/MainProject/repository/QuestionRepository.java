package com.example.MainProject.repository;
 
import com.example.MainProject.model.Question;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
 
@Repository

public interface QuestionRepository extends JpaRepository<Question, Long> {

    // You can add custom query methods here if needed, e.g., find by user, search by title/content

}
 