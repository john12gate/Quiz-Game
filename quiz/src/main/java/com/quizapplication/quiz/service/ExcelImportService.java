package com.quizapplication.quiz.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExcelImportService {
    void importQuizDataFromExcel(MultipartFile file) throws IOException;
}


