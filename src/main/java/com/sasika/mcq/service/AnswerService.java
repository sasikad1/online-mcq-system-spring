package com.sasika.mcq.service;

import com.sasika.mcq.dto.AnswerDTO;

import java.util.List;

public interface AnswerService {

    AnswerDTO createAnswer(AnswerDTO answerDTO);

    AnswerDTO getAnswerById(Long id);

    List<AnswerDTO> getAnswersByResultId(Long resultId);

    AnswerDTO updateAnswer(Long id, AnswerDTO answerDTO);

    void deleteAnswer(Long id);

}
