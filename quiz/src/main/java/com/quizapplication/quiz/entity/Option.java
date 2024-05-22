package com.quizapplication.quiz.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "options")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "option_text", nullable = false, length = 500)
    @NotBlank(message = "Option text cannot be blank")
    private String optionText;

    @Column(name = "is_correct", nullable = false)
    @NotNull(message = "isCorrect cannot be null")
    private Boolean isCorrect;

    @Column(nullable = false, length = 1000)
    @NotBlank(message = "Explanation cannot be blank")
    private String explanation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @NotNull(message = "Question cannot be null")
    @ToString.Exclude
    @JsonBackReference
    private Question question;


    @JsonCreator
    public Option(
            @JsonProperty("optionText") String optionText,
            @JsonProperty("isCorrect") Boolean isCorrect,
            @JsonProperty("explanation") String explanation) {
        this.optionText = optionText;
        this.isCorrect = isCorrect;
        this.explanation = explanation;
    }
}
