package com.quizapplication.quiz.serviceimpl;

import com.quizapplication.quiz.entity.Option;
import com.quizapplication.quiz.entity.Question;
import com.quizapplication.quiz.repository.QuestionRepository;
import com.quizapplication.quiz.service.ExcelExportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public byte[] exportQuizDataToExcel() throws IOException {
        List<Question> questions = questionRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Quiz Data");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Question", "Category", "Level", "Option 1 Text", "Correct 1", "Explanation 1",
                    "Option 2 Text", "Correct 2", "Explanation 2", "Option 3 Text", "Correct 3",
                    "Explanation 3", "Option 4 Text", "Correct 4", "Explanation 4"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Populate rows with question data
            int rowNum = 1;
            for (Question question : questions) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(question.getQuestionText());
                row.createCell(1).setCellValue(question.getCategory());
                row.createCell(2).setCellValue(question.getLevel());

                List<Option> options = question.getOptions();
                for (int i = 0; i < options.size(); i++) {
                    Option option = options.get(i);
                    row.createCell(3 + i * 3).setCellValue(option.getOptionText());
                    row.createCell(4 + i * 3).setCellValue(option.getIsCorrect() ? "Yes" : "No");
                    row.createCell(5 + i * 3).setCellValue(option.getExplanation());
                }
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
}
