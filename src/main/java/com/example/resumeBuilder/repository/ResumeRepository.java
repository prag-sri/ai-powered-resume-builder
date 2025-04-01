package com.example.resumeBuilder.repository;

import com.example.resumeBuilder.model.Resume;
import com.example.resumeBuilder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume,Long> {
    List<Resume> findByUser(User user);
}
