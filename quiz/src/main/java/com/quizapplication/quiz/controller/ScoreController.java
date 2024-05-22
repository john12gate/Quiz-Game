package com.quizapplication.quiz.controller;

import com.quizapplication.quiz.entity.Score;
import com.quizapplication.quiz.service.ScoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/scores")
@Tag(name = "Scores", description = "API for managing quiz scores")
public class ScoreController {

    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Operation(summary = "Retrieve top scores", description = "Fetches the list of top scores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of top scores"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/top")
    public ResponseEntity<List<Score>> getTopScores() {
        List<Score> scores = scoreService.getTopScores();
        return ResponseEntity.ok(scores);
    }

    @Operation(summary = "Save a new score", description = "Creates a new score entry in the quiz application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the score"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Score> saveScore(@Valid @RequestBody Score score) {
        try {
            Score savedScore = scoreService.saveScore(score);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedScore);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
