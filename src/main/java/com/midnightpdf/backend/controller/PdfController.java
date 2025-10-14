package com.midnightpdf.backend.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.midnightpdf.backend.service.PdfService;
import java.io.IOException;

@RestController
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/process-pdf")
    public ResponseEntity<byte[]> processPdf(
        @RequestParam("mode") String mode,
        @RequestParam("file") MultipartFile file
    ) throws IOException {
        byte[] pdfModificado;
        if ("oscuro".equalsIgnoreCase(mode)) {
            pdfModificado = pdfService.procesarPdfModoOscuro(file);
        } else {
            pdfModificado = file.getBytes();
        }

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=modificado.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdfModificado);
    }
}
