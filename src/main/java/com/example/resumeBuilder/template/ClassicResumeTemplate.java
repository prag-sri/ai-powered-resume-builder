package com.example.resumeBuilder.template;

import com.example.resumeBuilder.model.Resume;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component("classic")   // This registers the class as a Spring bean with the name "classic"
public class ClassicResumeTemplate implements ResumeTemplate{

    @Override
    public byte[] generatePdf(Resume resumeData) throws IOException {
        // Holds the PDF data in memory (as bytes) instead of saving it directly to a file
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        // Creates a blank PDF document using PDFBox
        PDDocument doc = new PDDocument();

        // Creates a new A4-sized pages and adds to the document
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);

        // Helps you write content onto a PDF
        PDPageContentStream stream = new PDPageContentStream(doc, page);

        stream.beginText();
        stream.setFont(PDType1Font.TIMES_ROMAN, 12);
        stream.setLeading(14.5f);  // Line spacing
        stream.newLineAtOffset(50,750);  // Move the cursor to x=50, y=750 (top leftish)
        stream.showText("Name: " + resumeData.getUser().getFullName());
        stream.newLine();
        stream.showText("Summary: " + resumeData.getSummary());
        stream.newLine();
        stream.showText("Skills: " + resumeData.getSkills());
        stream.newLine();
        stream.showText("Experience: " + resumeData.getExperience());

        stream.endText();
        stream.close();

        doc.save(output);  //  writes all the content of the PDF into that byte array
        doc.close();

        return output.toByteArray();
    }
}
