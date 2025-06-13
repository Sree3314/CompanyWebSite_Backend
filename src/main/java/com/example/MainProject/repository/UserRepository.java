
 
package com.example.MainProject.repository;
 
import com.example.MainProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import java.util.Optional;
 
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
 
    Optional<User> findByEmployeeId(Long employeeId);
 
    boolean existsByEmail(String email);
 
    boolean existsByEmployeeId(Long employeeId);
 
    // NEW: Added this method to allow finding a user by their personal email
    Optional<User> findByPersonalEmail(String personalEmail);
}
 
 