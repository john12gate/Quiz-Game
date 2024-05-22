package com.quizapplication.quiz.repository;

import com.quizapplication.quiz.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
