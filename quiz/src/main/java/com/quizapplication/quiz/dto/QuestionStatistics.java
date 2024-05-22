package com.quizapplication.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionStatistics {
    private long totalQuestions;
    private long totalCategories;
}
