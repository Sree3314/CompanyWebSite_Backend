
 
package com.example.MainProject.repository;
 
import com.example.MainProject.model.VerificationToken;
import com.example.MainProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import java.util.Optional;
 
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByUser(User user);
    Optional<VerificationToken> findByToken(String token);
    void deleteByUser(User user); // For cleaning up old tokens
}
 
 