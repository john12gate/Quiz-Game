package com.quizapplication.quiz.service;

import com.quizapplication.quiz.entity.Question;
import com.quizapplication.quiz.dto.QuestionStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuizService {

    Page<Question> getAllQuestions(Pageable pageable);

    Question getQuestionById(Long id);

    Question addQuestion(Question question);

    void deleteQuestion(Long id);

    List<Question> bulkAddQuestions(List<Question> questions);

    Page<Question> searchQuestions(String keyword, String category, String difficulty, Pageable pageable);

    Page<Question> getQuestionsByCategory(String category, Pageable pageable);

    List<Question> updateQuestions(List<Question> questions);

    Question updateQuestion(Long id, Question questionDetails);

    List<Question> getRandomQuestions(int count);

    QuestionStatistics getQuestionStatistics();
}
