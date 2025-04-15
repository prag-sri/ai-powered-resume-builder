package com.example.resumeBuilder.template;

import com.example.resumeBuilder.model.Resume;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component("modern")
public class ModernResumeTemplate implements ResumeTemplate{

    @Override
    public byte[] generatePdf(Resume resumeData) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);

        PDPageContentStream stream = new PDPageContentStream(doc, page);
        stream.beginText();
        stream.setFont(PDType1Font.HELVETICA_BOLD, 11);
        stream.setLeading(14f);
        stream.newLineAtOffset(50,750);

        stream.showText("Name: " + resumeData.getUser().getFullName());
        stream.newLine();
        stream.showText("Email: " + resumeData.getUser().getEmail());
        stream.newLine();
        stream.showText("Summary: " + resumeData.getSummary());
        stream.newLine();
        stream.showText("Skills: " + resumeData.getSkills());
        stream.newLine();
        stream.showText("Experience: " + resumeData.getExperience());
        stream.endText();

        stream.close();

        doc.save(output);
        doc.close();

        return output.toByteArray();
    }
}

