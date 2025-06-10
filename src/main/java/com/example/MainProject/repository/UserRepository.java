package com.example.MainProject.repository;

import com.example.MainProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	Optional<User> findByEmployeeId(String employeeId);

	boolean existsByEmail(String email);

	boolean existsByEmployeeId(String employeeId);
}
