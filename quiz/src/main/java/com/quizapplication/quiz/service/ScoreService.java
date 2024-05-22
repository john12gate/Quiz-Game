package com.quizapplication.quiz.service;

import com.quizapplication.quiz.entity.Score;
import java.util.List;

public interface ScoreService {
    List<Score> getTopScores();
    Score saveScore(Score score);
    Score getScoreById(Long id);
    void deleteScore(Long id);
}
