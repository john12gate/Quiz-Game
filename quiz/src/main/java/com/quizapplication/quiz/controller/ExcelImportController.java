package com.quizapplication.quiz.controller;

import com.quizapplication.quiz.service.ExcelImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/import")
@Tag(name = "Import Management", description = "Operations pertaining to importing quiz data from Excel files")
public class ExcelImportController {

    @Autowired
    private ExcelImportService excelImportService;

    @PostMapping("/excel")
    @Operation(summary = "Import quiz data from an Excel file", description = "Provide an Excel file to import quiz data into the system. The file must adhere to the specified format.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz data imported successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided in the file"),
            @ApiResponse(responseCode = "500", description = "Failed to import quiz data due to server error")
    })
    public ResponseEntity<String> importQuizFromExcel(
            @Parameter(description = "Excel file containing quiz data", required = true) @RequestParam("file") MultipartFile file) {
        try {
            excelImportService.importQuizDataFromExcel(file);
            return ResponseEntity.ok("Quiz data imported successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to import quiz data: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data: " + e.getMessage());
        }
    }

    @GetMapping("/template")
    @Operation(summary = "Download Excel template", description = "Download an Excel template for quiz data import. The template provides the required format for the import process.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Template downloaded successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to download the template due to server error")
    })
    public ResponseEntity<byte[]> downloadTemplate() throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Template");
            Row headerRow = sheet.createRow(0);

            String[] headers = {"Question", "Category", "Level", "Option 1 Text", "Correct 1", "Explanation 1", "Option 2 Text", "Correct 2", "Explanation 2", "Option 3 Text", "Correct 3", "Explanation 3", "Option 4 Text", "Correct 4", "Explanation 4"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            workbook.write(out);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=template.xlsx")
                    .body(out.toByteArray());
        }
    }
}
