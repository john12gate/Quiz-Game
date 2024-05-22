package com.quizapplication.quiz.service;

import java.io.IOException;

public interface ExcelExportService {
    byte[] exportQuizDataToExcel() throws IOException;
}
