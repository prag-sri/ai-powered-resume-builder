package com.example.resumeBuilder.controller;

import com.example.resumeBuilder.model.Resume;
import com.example.resumeBuilder.service.ResumeAiService;
import com.example.resumeBuilder.service.ResumeService;
import com.example.resumeBuilder.template.ResumeTemplate;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/resume")
public class ResumeExportController {

    private final ResumeService resumeService;
    private final Map<String, ResumeTemplate> templateMap;

    public ResumeExportController(ResumeService resumeService, Map<String, ResumeTemplate> templateMap) {
        this.resumeService = resumeService;
        this.templateMap = templateMap;
    }

    @GetMapping("/export")
    // This method returns a PDF in byte array form
    public ResponseEntity<byte[]> exportResume(@RequestParam Long resumeId, @RequestParam String template) throws IOException {
        Resume resume =resumeService.getResumeById(resumeId);

        // Looks up the resume template
        ResumeTemplate generator = templateMap.get(template);

        byte[] pdfBytes = generator.generatePdf(resume);

        // Creates HTTP headers and sets content type to application/pdf so the browser knows it's a PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        // It tells the browser to download it as resume.pdf
        headers.setContentDisposition(ContentDisposition.attachment().filename("resume.pdf").build());

        // Sends the PDF bytes + headers + a 200 OK status as response
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
