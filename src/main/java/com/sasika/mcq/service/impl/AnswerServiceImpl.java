package com.sasika.mcq.service.impl;

import com.sasika.mcq.dto.AnswerDTO;
import com.sasika.mcq.entity.Answer;
import com.sasika.mcq.entity.Question;
import com.sasika.mcq.entity.Result;
import com.sasika.mcq.repo.AnswerRepository;
import com.sasika.mcq.repo.QuestionRepository;
import com.sasika.mcq.repo.ResultRepository;
import com.sasika.mcq.service.AnswerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final ResultRepository resultRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    public AnswerServiceImpl(AnswerRepository answerRepository,
                             ResultRepository resultRepository,
                             QuestionRepository questionRepository,
                             ModelMapper modelMapper) {
        this.answerRepository = answerRepository;
        this.resultRepository = resultRepository;
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AnswerDTO createAnswer(AnswerDTO answerDTO) {
        // Optionally check if related result exists
        Result result = resultRepository.findById(answerDTO.getResultId())
                .orElseThrow(() -> new RuntimeException("Result not found with ID: " + answerDTO.getResultId()));

        Answer answer = modelMapper.map(answerDTO, Answer.class);
        answer.setResult(result);

        Answer savedAnswer = answerRepository.save(answer);
        return modelMapper.map(savedAnswer, AnswerDTO.class);
    }

    @Override
    public AnswerDTO getAnswerById(Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found with ID: " + id));
        return modelMapper.map(answer, AnswerDTO.class);
    }

    @Override
    public List<AnswerDTO> getAnswersByResultId(Long resultId) {
        List<Answer> answers = answerRepository.findByResultId(resultId);
        return answers.stream()
                .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AnswerDTO updateAnswer(Long id, AnswerDTO answerDTO) {
        Answer existingAnswer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found with ID: " + id));

        // ✅ Fetch and set the Question object
        Question question = questionRepository.findById(answerDTO.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + answerDTO.getQuestionId()));
        existingAnswer.setQuestion(question);

        existingAnswer.setSelectedOption(answerDTO.getSelectedOption());
        existingAnswer.setCorrect(answerDTO.isCorrect());

        Answer updated = answerRepository.save(existingAnswer);
        return modelMapper.map(updated, AnswerDTO.class);
    }

    @Override
    public void deleteAnswer(Long id) {
        if (!answerRepository.existsById(id)) {
            throw new RuntimeException("Answer not found with ID: " + id);
        }
        answerRepository.deleteById(id);
    }
}
