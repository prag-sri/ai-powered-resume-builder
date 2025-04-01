package com.example.resumeBuilder.repository;

import com.example.resumeBuilder.model.AISuggestion;
import com.example.resumeBuilder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AISuggestionRepository extends JpaRepository<AISuggestion,Long> {
    Optional<AISuggestion> findByUser(User user);
}
