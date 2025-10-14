package com.midnightpdf.backend.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
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
            PDPage originalPage = originalDoc.getPage(page);
            PDRectangle originalMediaBox = originalPage.getMediaBox();

            BufferedImage image = pdfRenderer.renderImageWithDPI(page, 400);

            BufferedImage darkImage = new BufferedImage(
                image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB
            );
            int backgroundRGB = (25 << 16) | (25 << 8) | 25; // #191919
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;
                    int brightness = (r + g + b) / 3;
                    if (brightness < 128) {
                        darkImage.setRGB(x, y, 0xFFFFFF);
                    } else {
                        darkImage.setRGB(x, y, backgroundRGB);
                    }
                }
            }

            PDPage newPage = new PDPage(originalMediaBox);
            darkDoc.addPage(newPage);

            PDImageXObject pdImage = LosslessFactory.createFromImage(darkDoc, darkImage);
            PDPageContentStream contentStream = new PDPageContentStream(darkDoc, newPage, AppendMode.OVERWRITE, false);

            contentStream.drawImage(pdImage, 0, 0, originalMediaBox.getWidth(), originalMediaBox.getHeight());
            contentStream.close();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        darkDoc.save(outputStream);
        darkDoc.close();
        originalDoc.close();

        return outputStream.toByteArray();
    }
}