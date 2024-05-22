package com.quizapplication.quiz.repository;

import com.quizapplication.quiz.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE " +
            "(:keyword IS NULL OR q.questionText LIKE %:keyword%) AND " +
            "(:category IS NULL OR q.category = :category) AND " +
            "(:difficulty IS NULL OR q.level = :difficulty)")
    Page<Question> findByKeywordAndCategoryAndDifficulty(String keyword, String category, String difficulty, Pageable pageable);

    Page<Question> findByCategory(String category, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT q.category) FROM Question q")
    long countDistinctCategories();
}
