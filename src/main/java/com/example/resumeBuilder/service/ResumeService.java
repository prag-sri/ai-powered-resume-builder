package com.example.resumeBuilder.service;

import com.example.resumeBuilder.model.Resume;
import com.example.resumeBuilder.model.User;
import com.example.resumeBuilder.repository.ResumeRepository;
import com.example.resumeBuilder.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class ResumeService {

    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final ResumeAiService resumeAiService;

    public ResumeService(UserRepository userRepository, ResumeRepository resumeRepository, ResumeAiService resumeAiService) {
        this.userRepository = userRepository;
        this.resumeRepository = resumeRepository;
        this.resumeAiService = resumeAiService;
    }

    public void createResume(Resume resume){
        // Check if user id exists
        Long userId = resume.getUser().getId();
        Optional<User> userOpt = userRepository.findById(userId);

        if(userOpt.isEmpty())
                throw new IllegalArgumentException("User with ID " + userId + " not found!");

        // Set the actual user entity to avoid detached entity issues
        resume.setUser(userOpt.get());

        // Generate summary using OpenAI
        String userInput = resume.getExperience() + " " + resume.getSkills();
        String summary = resumeAiService.generateSummary(userInput);
        resume.setSummary(summary);

        resumeRepository.save(resume);
    }

    public void editResume(Long id, Resume resume){
        Resume existingResume = resumeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resume with ID " + id + " not found!"));

        existingResume.setId(id);
        existingResume.setUser(resume.getUser());
        existingResume.setSkills(resume.getSkills());
        existingResume.setExperience(resume.getExperience());

        // Generate summary using OpenAI
        String userInput = resume.getExperience() + " " + resume.getSkills();
        String summary = resumeAiService.generateSummary(userInput);
        existingResume.setSummary(summary);

        resumeRepository.save(existingResume);
    }

    public Resume getResumeById(Long id){
        return resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
    }

    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    public void deleteResumeById(Long id) {
        resumeRepository.deleteById(id);
    }
}
