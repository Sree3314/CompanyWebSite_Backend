package com.example.MainProject.repository;
 
import com.example.MainProject.model.Answer;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
 
@Repository

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    // Find all answers for a specific question

    // List<Answer> findByQuestionIdOrderByPostedAtAsc(Long questionId);

}
 