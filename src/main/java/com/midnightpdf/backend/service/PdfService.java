package com.midnightpdf.backend.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {
    public byte[] procesarPdfModoOscuro(MultipartFile file) throws IOException {
        PDDocument originalDoc = PDDocument.load(file.getInputStream());
        PDFRenderer pdfRenderer = new PDFRenderer(originalDoc);

        PDDocument darkDoc = new PDDocument();

        for (int page = 0; page < originalDoc.getNumberOfPages(); ++page) {

            BufferedImage image = pdfRenderer.renderImageWithDPI(page, 200);


            BufferedImage darkImage = new BufferedImage(
                image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB
            );
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    int r = 255 - ((rgb >> 16) & 0xFF);
                    int g = 255 - ((rgb >> 8) & 0xFF);
                    int b = 255 - (rgb & 0xFF);
                    int invertedRGB = (r << 16) | (g << 8) | b;
                    darkImage.setRGB(x, y, invertedRGB);
                }
            }

            PDPage newPage = new PDPage();
            darkDoc.addPage(newPage);
            PDImageXObject pdImage = LosslessFactory.createFromImage(darkDoc, darkImage);
            PDPageContentStream contentStream = new PDPageContentStream(darkDoc, newPage);
            contentStream.drawImage(pdImage, 0, 0, newPage.getMediaBox().getWidth(), newPage.getMediaBox().getHeight());
            contentStream.close();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        darkDoc.save(outputStream);
        darkDoc.close();
        originalDoc.close();

        return outputStream.toByteArray();
    }
}