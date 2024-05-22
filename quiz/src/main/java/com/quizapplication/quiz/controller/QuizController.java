package com.quizapplication.quiz.controller;

import com.quizapplication.quiz.entity.Question;
import com.quizapplication.quiz.exception.ResourceNotFoundException;
import com.quizapplication.quiz.service.QuizService;
import com.quizapplication.quiz.dto.QuestionStatistics;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@Tag(name = "Quiz Questions", description = "API for managing quiz questions")
@CrossOrigin(origins = "*")
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    @Autowired
    private QuizService quizService;

    @Operation(summary = "Retrieve all questions", description = "Fetches all questions with pagination support")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of questions"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Page<Question>> getAllQuestions(Pageable pageable) {
        try {
            Page<Question> questions = quizService.getAllQuestions(pageable);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving questions: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Retrieve a question by ID", description = "Fetches a specific question based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the question"),
            @ApiResponse(responseCode = "404", description = "Question not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        try {
            Question question = quizService.getQuestionById(id);
            return new ResponseEntity<>(question, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logger.error("Question not found: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error retrieving question: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Add a new question", description = "Creates a new question in the quiz application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the question"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        try {
            logger.info("Received request to add question: {}", question);
            Question createdQuestion = quizService.addQuestion(question);
            return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update an existing question", description = "Updates an existing question in the quiz application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the question"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Question not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question questionDetails) {
        try {
            Question updatedQuestion = quizService.updateQuestion(id, questionDetails);
            return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logger.error("Question not found: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a question by ID", description = "Removes a specific question based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the question"),
            @ApiResponse(responseCode = "404", description = "Question not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        try {
            quizService.deleteQuestion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            logger.error("Question not found: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error deleting question: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Bulk add questions", description = "Adds multiple questions in a single request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added the questions"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/bulk")
    public ResponseEntity<List<Question>> bulkAddQuestions(@RequestBody List<Question> questions) {
        try {
            logger.info("Received request to bulk add questions: {}", questions);
            List<Question> createdQuestions = quizService.bulkAddQuestions(questions);
            return new ResponseEntity<>(createdQuestions, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Search questions", description = "Searches for questions based on criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of questions"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search")
    public ResponseEntity<Page<Question>> searchQuestions(
            @RequestParam String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficulty,
            Pageable pageable) {
        try {
            Page<Question> questions = quizService.searchQuestions(keyword, category, difficulty, pageable);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error searching questions: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get questions by category", description = "Fetches questions filtered by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of questions"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Question>> getQuestionsByCategory(@PathVariable String category, Pageable pageable) {
        try {
            Page<Question> questions = quizService.getQuestionsByCategory(category, pageable);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving questions by category: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update multiple questions", description = "Updates multiple questions in a single request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the questions"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/bulk")
    public ResponseEntity<List<Question>> updateQuestions(@RequestBody List<Question> questions) {
        try {
            List<Question> updatedQuestions = quizService.updateQuestions(questions);
            return new ResponseEntity<>(updatedQuestions, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get random questions", description = "Fetches a random set of questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the random questions"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/random")
    public ResponseEntity<List<Question>> getRandomQuestions(@RequestParam int count) {
        try {
            List<Question> questions = quizService.getRandomQuestions(count);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving random questions: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get question statistics", description = "Fetches statistics about the questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the question statistics"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/statistics", produces = "application/json")
    public ResponseEntity<QuestionStatistics> getQuestionStatistics() {
        try {
            QuestionStatistics statistics = quizService.getQuestionStatistics();
            return new ResponseEntity<>(statistics, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving question statistics: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
