package com.example.resumeBuilder.template;

import com.example.resumeBuilder.model.Resume;

import java.io.IOException;

public interface ResumeTemplate {
    byte[] generatePdf(Resume resumeData) throws IOException;
}
