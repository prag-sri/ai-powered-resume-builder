package com.example.resumeBuilder.repository;

import java.util.List;
import com.example.resumeBuilder.model.JobRecommendation;
import com.example.resumeBuilder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRecommendationRepository extends JpaRepository<JobRecommendation,Long> {
    List<JobRecommendation> findByUser(User user);
}
