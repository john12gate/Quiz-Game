package com.quizapplication.quiz.service.impl;

import com.quizapplication.quiz.entity.Score;
import com.quizapplication.quiz.exception.ResourceNotFoundException;
import com.quizapplication.quiz.repository.ScoreRepository;
import com.quizapplication.quiz.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    public List<Score> getTopScores() {
        return scoreRepository.findTop10ByOrderByScoreDesc();
    }

    @Override
    public Score saveScore(Score score) {
        return scoreRepository.save(score);
    }

    @Override
    public Score getScoreById(Long id) {
        return scoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found with id: " + id));
    }

    @Override
    public void deleteScore(Long id) {
        if (!scoreRepository.existsById(id)) {
            throw new ResourceNotFoundException("Score not found with id: " + id);
        }
        scoreRepository.deleteById(id);
    }
}
