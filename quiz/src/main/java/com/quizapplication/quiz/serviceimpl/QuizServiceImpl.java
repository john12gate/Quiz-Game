package com.quizapplication.quiz.serviceimpl;

import com.quizapplication.quiz.dto.QuestionStatistics;
import com.quizapplication.quiz.entity.Option;
import com.quizapplication.quiz.entity.Question;
import com.quizapplication.quiz.exception.ResourceNotFoundException;
import com.quizapplication.quiz.repository.QuestionRepository;
import com.quizapplication.quiz.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Page<Question> getAllQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
    }

    @Override
    @Transactional
    public Question addQuestion(Question question) {
        logger.info("Adding question: {}", question);
        validateQuestion(question);
        Question savedQuestion = questionRepository.save(question);
        logger.info("Question saved successfully: {}", savedQuestion);
        return savedQuestion;
    }

    @Override
    @Transactional
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question not found with id: " + id);
        }
        questionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Question> bulkAddQuestions(List<Question> questions) {
        logger.info("Bulk adding questions: {}", questions);
        questions.forEach(this::validateQuestion);
        List<Question> savedQuestions = questionRepository.saveAll(questions);
        logger.info("Questions saved successfully: {}", savedQuestions);
        return savedQuestions;
    }

    @Override
    public Page<Question> searchQuestions(String keyword, String category, String difficulty, Pageable pageable) {
        logger.info("Searching questions with keyword: {}, category: {}, difficulty: {}", keyword, category, difficulty);
        return questionRepository.findByKeywordAndCategoryAndDifficulty(keyword, category, difficulty, pageable);
    }

    @Override
    public Page<Question> getQuestionsByCategory(String category, Pageable pageable) {
        logger.info("Fetching questions by category: {}", category);
        return questionRepository.findByCategory(category, pageable);
    }

    @Override
    @Transactional
    public List<Question> updateQuestions(List<Question> questions) {
        logger.info("Updating questions: {}", questions);
        List<Question> updatedQuestions = questions.stream().map(question -> {
            if (!questionRepository.existsById(question.getId())) {
                throw new ResourceNotFoundException("Question not found with id: " + question.getId());
            }
            validateQuestion(question);
            return questionRepository.save(question);
        }).collect(Collectors.toList());
        logger.info("Questions updated successfully: {}", updatedQuestions);
        return updatedQuestions;
    }

    @Override
    @Transactional
    public Question updateQuestion(Long id, Question questionDetails) {
        logger.info("Updating question with id: {}", id);
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        validateQuestion(questionDetails);
        existingQuestion.setQuestionText(questionDetails.getQuestionText());
        existingQuestion.setCategory(questionDetails.getCategory());
        existingQuestion.setLevel(questionDetails.getLevel());
        existingQuestion.setOptions(questionDetails.getOptions());
        for (Option option : existingQuestion.getOptions()) {
            option.setQuestion(existingQuestion);
        }
        Question updatedQuestion = questionRepository.save(existingQuestion);
        logger.info("Question updated successfully: {}", updatedQuestion);
        return updatedQuestion;
    }

    @Override
    public List<Question> getRandomQuestions(int count) {
        logger.info("Fetching {} random questions", count);
        List<Question> allQuestions = questionRepository.findAll();
        if (count > allQuestions.size()) {
            throw new IllegalArgumentException("Requested number of questions exceeds the available questions");
        }
        Random random = new Random();
        List<Question> randomQuestions = random.ints(0, allQuestions.size())
                .distinct()
                .limit(count)
                .mapToObj(allQuestions::get)
                .collect(Collectors.toList());
        logger.info("Random questions fetched successfully: {}", randomQuestions);
        return randomQuestions;
    }

    @Override
    public QuestionStatistics getQuestionStatistics() {
        logger.info("Fetching question statistics");
        long totalQuestions = questionRepository.count();
        long totalCategories = questionRepository.countDistinctCategories();
        QuestionStatistics statistics = new QuestionStatistics(totalQuestions, totalCategories);
        logger.info("Question statistics fetched successfully: {}", statistics);
        return statistics;
    }

    private void validateQuestion(Question question) {
        long correctAnswersCount = question.getOptions().stream().filter(Option::getIsCorrect).count();
        if (correctAnswersCount != 1) {
            logger.error("Validation failed: There must be exactly one correct answer.");
            throw new IllegalArgumentException("There must be exactly one correct answer.");
        }
        if (question.getOptions().size() > 4) {
            logger.error("Validation failed: There can be a maximum of 4 options.");
            throw new IllegalArgumentException("There can be a maximum of 4 options.");
        }
    }
}
