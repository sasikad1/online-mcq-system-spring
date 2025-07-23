package com.sasika.mcq.service;

import com.sasika.mcq.dto.AnswerDTO;

import java.util.List;

public interface AnswerService {

    AnswerDTO createAnswer(AnswerDTO answerDTO);

    List<AnswerDTO> getAnswersByQuestionAndUser(Long questionId, Long userId);

    List<AnswerDTO> getAnswersByUserAndExam(Long userId, Long examId);

    ///   //////////////////////////

    AnswerDTO getAnswerById(Long id);

    List<AnswerDTO> getAnswersByResultId(Long resultId);

    AnswerDTO updateAnswer(Long id, AnswerDTO answerDTO);

    void deleteAnswer(Long id);

}
