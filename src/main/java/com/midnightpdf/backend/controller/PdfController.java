package com.midnightpdf.backend.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.midnightpdf.backend.service.PdfService;
import java.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @Operation(
        summary = "Procesa un PDF en modo oscuro",
        description = "Recibe un archivo PDF y lo convierte a modo oscuro (fondo #191919, texto blanco)."
    )
    @ApiResponse(responseCode = "200", description = "PDF procesado correctamente")
    @ApiResponse(responseCode = "400", description = "Archivo no válido")
   @PostMapping(value = "/process-pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> processPdf(
        @Parameter(description = "Modo de procesamiento (oscuro)") @RequestParam("mode") String mode,
        @Parameter(description = "Archivo PDF a procesar") @RequestParam("file") MultipartFile file
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
