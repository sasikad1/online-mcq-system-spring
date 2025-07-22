package com.sasika.mcq.service;

import com.sasika.mcq.dto.QuestionDTO;

import java.util.List;

public interface QuestionService {
    QuestionDTO createQuestion(Long examId, QuestionDTO questionDTO);

    List<QuestionDTO> getQuestionsByExamId(Long examId);

    QuestionDTO getQuestionById(Long questionId);

    QuestionDTO updateQuestion(Long questionId, QuestionDTO questionDTO);

    void deleteQuestion(Long questionId);
}
