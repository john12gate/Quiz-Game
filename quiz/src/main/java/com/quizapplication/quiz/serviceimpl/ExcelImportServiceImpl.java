package com.quizapplication.quiz.service.impl;

import com.quizapplication.quiz.entity.Option;
import com.quizapplication.quiz.entity.Question;
import com.quizapplication.quiz.repository.QuestionRepository;
import com.quizapplication.quiz.service.ExcelImportService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelImportServiceImpl implements ExcelImportService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelImportServiceImpl.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    @Transactional
    public void importQuizDataFromExcel(MultipartFile file) throws IOException {
        List<Question> questions = parseExcelFile(file);
        questionRepository.saveAll(questions);
    }

    private List<Question> parseExcelFile(MultipartFile file) throws IOException {
        List<Question> questions = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Skip the header row
                    continue;
                }

                try {
                    validateRow(row);

                    Question question = new Question();
                    question.setQuestionText(row.getCell(0).getStringCellValue());
                    question.setCategory(row.getCell(1).getStringCellValue());
                    question.setLevel(row.getCell(2).getStringCellValue());

                    List<Option> options = new ArrayList<>();
                    for (int i = 3; i < row.getLastCellNum(); i += 3) {
                        if (row.getCell(i) != null && row.getCell(i).getStringCellValue() != null && !row.getCell(i).getStringCellValue().isEmpty()) {
                            Option option = new Option();
                            option.setOptionText(row.getCell(i).getStringCellValue());
                            option.setIsCorrect(row.getCell(i + 1).getStringCellValue().equalsIgnoreCase("yes"));
                            option.setExplanation(row.getCell(i + 2).getStringCellValue());
                            option.setQuestion(question);
                            options.add(option);
                        }
                    }

                    question.setOptions(options);
                    questions.add(question);
                } catch (Exception e) {
                    logger.error("Error processing row " + row.getRowNum(), e);
                }
            }
        }
        return questions;
    }

    private void validateRow(Row row) {
        if (StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
            throw new IllegalArgumentException("Question text cannot be blank");
        }
        if (StringUtils.isBlank(row.getCell(1).getStringCellValue())) {
            throw new IllegalArgumentException("Category cannot be blank");
        }
        if (StringUtils.isBlank(row.getCell(2).getStringCellValue())) {
            throw new IllegalArgumentException("Level cannot be blank");
        }

        for (int i = 3; i < row.getLastCellNum(); i += 3) {
            if (row.getCell(i) != null && StringUtils.isBlank(row.getCell(i).getStringCellValue())) {
                throw new IllegalArgumentException("Option text cannot be blank");
            }
            if (row.getCell(i + 1) != null && StringUtils.isBlank(row.getCell(i + 1).getStringCellValue())) {
                throw new IllegalArgumentException("Correctness cannot be blank");
            }
            if (row.getCell(i + 2) != null && StringUtils.isBlank(row.getCell(i + 2).getStringCellValue())) {
                throw new IllegalArgumentException("Explanation cannot be blank");
            }
        }
    }
}
