package com.sasika.mcq.service.impl;

import com.sasika.mcq.dto.AnswerDTO;
import com.sasika.mcq.entity.Answer;
import com.sasika.mcq.entity.Question;
import com.sasika.mcq.entity.Result;
import com.sasika.mcq.entity.User;
import com.sasika.mcq.repo.AnswerRepository;
import com.sasika.mcq.repo.QuestionRepository;
import com.sasika.mcq.repo.ResultRepository;
import com.sasika.mcq.repo.UserRepository;
import com.sasika.mcq.service.AnswerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final ResultRepository resultRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    public AnswerServiceImpl(AnswerRepository answerRepository,
                             ResultRepository resultRepository,
                             UserRepository userRepository,
                             QuestionRepository questionRepository,
                             ModelMapper modelMapper) {
        this.answerRepository = answerRepository;
        this.resultRepository = resultRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    //create  answer without reasult
    @Override
    public AnswerDTO createAnswer(AnswerDTO answerDTO) {
        Answer answer = modelMapper.map(answerDTO, Answer.class);

        Question question = questionRepository.findById(answer.getQuestion().getId())
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + answer.getQuestion().getId()));

        if (question.getCorrectOption() != null && question.getCorrectOption().equals(answer.getSelectedOption())) {
            answer.setCorrect(true);
        }

        Answer savedAnswer = answerRepository.save(answer);
        return modelMapper.map(savedAnswer, AnswerDTO.class);
    }

//    @Override
//    public AnswerDTO createAnswer(AnswerDTO answerDTO) {
//        Answer answer = new Answer();
//
//        // Set the question
//        Question question = questionRepository.findById(answerDTO.getQuestionId())
//                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + answerDTO.getQuestionId()));
//        answer.setQuestion(question);
//
//        // Set the user
//        User user = userRepository.findById(answerDTO.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + answerDTO.getUserId()));
//        answer.setUser(user);
//
//        // Set the selected option
//        answer.setSelectedOption(answerDTO.getSelectedOption());
//
//        // Check correctness
//        if (question.getCorrectOption() != null && question.getCorrectOption().equals(answer.getSelectedOption())) {
//            answer.setCorrect(true);
//        }
//
//        Answer savedAnswer = answerRepository.save(answer);
//        return modelMapper.map(savedAnswer, AnswerDTO.class);
//    }


    @Override
    public List<AnswerDTO> getAnswersByQuestionAndUser(Long questionId, Long userId) {
        List<Answer> answers = answerRepository.findByQuestionIdAndUserId(questionId, userId);
        return answers.stream()
                .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                .toList();
    }


    @Override
    public List<AnswerDTO> getAnswersByUserAndExam(Long userId, Long examId) {
        List<Answer> answers = answerRepository.findByUserIdAndQuestionExamId(userId, examId);
        return answers.stream()
                .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                .toList();
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
