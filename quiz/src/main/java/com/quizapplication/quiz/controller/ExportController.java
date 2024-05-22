package com.quizapplication.quiz.controller;

import com.quizapplication.quiz.service.ExcelExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * ExportController handles the export of quiz data to various formats.
 * This controller currently supports exporting quiz data to an Excel file.
 */
@RestController
@RequestMapping("/api/export")
@Tag(name = "Export API", description = "Endpoints for exporting quiz data")
public class ExportController {

    private final ExcelExportService excelExportService;

    @Autowired
    public ExportController(ExcelExportService excelExportService) {
        this.excelExportService = excelExportService;
    }

    /**
     * Exports all quiz data to an Excel file.
     * <p>
     * This endpoint generates an Excel file containing all the quiz data and prompts the user to download it.
     * </p>
     *
     * @return A ResponseEntity containing the byte array of the Excel file, along with appropriate headers.
     * @throws IOException if an I/O error occurs during file generation.
     */
    @Operation(summary = "Export Quiz Data to Excel", description = "Generates an Excel file containing all quiz data and returns it for download.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully exported quiz data to Excel"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred while exporting quiz data")
    })
    @GetMapping("/excel")
    public ResponseEntity<byte[]> exportQuizToExcel() {
        try {
            byte[] data = excelExportService.exportQuizDataToExcel();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=quiz_data.xlsx")
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .body(data);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
