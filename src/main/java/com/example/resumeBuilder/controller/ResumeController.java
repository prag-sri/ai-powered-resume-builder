package com.example.resumeBuilder.controller;

import com.example.resumeBuilder.model.Resume;
import com.example.resumeBuilder.service.ResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping
    public ResponseEntity<String> createResume(@RequestBody Resume resume){
        resumeService.createResume(resume);
        return new ResponseEntity<>("Resume created successfully!", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editResume(@PathVariable Long id, @RequestBody Resume resume){
        resumeService.editResume(id,resume);
        return new ResponseEntity<>("Resume edited successfully!", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resume> getResumeById(@PathVariable Long id) {
        return new ResponseEntity<>(resumeService.getResumeById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Resume>> getAllResumes() {
        return new ResponseEntity<>(resumeService.getAllResumes(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResumeById(@PathVariable Long id) {
        resumeService.deleteResumeById(id);
        return new ResponseEntity<>("Resume deleted successfully!", HttpStatus.OK);
    }
}
