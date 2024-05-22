package com.quizapplication.quiz.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text", nullable = false, length = 500)
    @NotBlank(message = "Question text cannot be blank")
    @Size(max = 500, message = "Question text cannot exceed 500 characters")
    private String questionText;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Category cannot be blank")
    @Size(max = 100, message = "Category cannot exceed 100 characters")
    private String category;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Level cannot be blank")
    @Size(max = 50, message = "Level cannot exceed 50 characters")
    private String level;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @NotEmpty(message = "Options list cannot be empty")
    @ToString.Exclude
    @JsonManagedReference
    private List<Option> options;

    @JsonCreator
    public Question(
            @JsonProperty("questionText") String questionText,
            @JsonProperty("category") String category,
            @JsonProperty("level") String level,
            @JsonProperty("options") List<Option> options) {
        this.questionText = questionText;
        this.category = category;
        this.level = level;
        this.options = options;

        for (Option option : this.options) {
            option.setQuestion(this);
        }
    }

    @PrePersist
    @PreUpdate
    private void validate() {
        if (questionText == null || questionText.trim().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be null or empty");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        if (level == null || level.trim().isEmpty()) {
            throw new IllegalArgumentException("Level cannot be null or empty");
        }
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("Options list cannot be null or empty");
        }

        long correctAnswersCount = options.stream().filter(Option::getIsCorrect).count();
        if (correctAnswersCount != 1) {
            throw new IllegalArgumentException("There must be exactly one correct answer.");
        }
        if (options.size() > 4) {
            throw new IllegalArgumentException("There can be a maximum of 4 options.");
        }
    }
}
