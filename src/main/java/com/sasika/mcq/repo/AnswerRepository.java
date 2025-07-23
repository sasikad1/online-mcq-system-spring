package com.sasika.mcq.repo;

import com.sasika.mcq.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByResultId(Long resultId);

    List<Answer> findByQuestionIdAndUserId(Long questionId, Long userId);

    List<Answer> findByUserIdAndQuestionExamId(Long userId, Long examId);

}
