package com.sasika.mcq.service.impl;

import com.sasika.mcq.dto.AnswerDTO;
import com.sasika.mcq.dto.ResultDTO;
import com.sasika.mcq.entity.*;
import com.sasika.mcq.repo.*;
import com.sasika.mcq.service.ResultService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ResultDTO createResult(ResultDTO resultDTO) {
        Result result = new Result();

        // 🧑 Set User
        User user = userRepository.findById(resultDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + resultDTO.getUserId()));
        result.setUser(user);

        // 📝 Set Exam
        Exam exam = examRepository.findById(resultDTO.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + resultDTO.getExamId()));
        result.setExam(exam);

        // 📅 Set Submitted Time
        result.setSubmittedAt(LocalDateTime.now());

        // 💾 Save result early to attach to answers (because answers need a result reference)
        Result savedResult = resultRepository.save(result);

        // ✅ Save Answers without mutating score inside lambda
        List<Answer> answers = resultDTO.getAnswers().stream().map(answerDTO -> {
            Answer answer = new Answer();
            answer.setResult(savedResult);

            Question question = questionRepository.findById(answerDTO.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found with ID: " + answerDTO.getQuestionId()));
            answer.setQuestion(question);

            answer.setSelectedOption(answerDTO.getSelectedOption());

            boolean isCorrect = question.getCorrectOption().equals(answerDTO.getSelectedOption());
            answer.setCorrect(isCorrect);

            return answer;
        }).collect(Collectors.toList());

        answerRepository.saveAll(answers);

        // Calculate score AFTER answers are created
        int score = (int) answers.stream().filter(Answer::isCorrect).count();

        // 🧮 Update score and resave
        savedResult.setScore(score);
        savedResult.setAnswers(answers);
        Result finalResult = resultRepository.save(savedResult);

        // 🛠️ Map and return DTO
        ResultDTO responseDTO = modelMapper.map(finalResult, ResultDTO.class);
        List<AnswerDTO> answerDTOList = finalResult.getAnswers().stream()
                .map(a -> modelMapper.map(a, AnswerDTO.class))
                .collect(Collectors.toList());
        responseDTO.setAnswers(answerDTOList);

        return responseDTO;
    }


    @Override
    public ResultDTO getResultById(Long id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found with ID: " + id));

        ResultDTO resultDTO = modelMapper.map(result, ResultDTO.class);
        List<AnswerDTO> answers = result.getAnswers().stream()
                .map(a -> modelMapper.map(a, AnswerDTO.class))
                .collect(Collectors.toList());
        resultDTO.setAnswers(answers);

        return resultDTO;
    }

    @Override
    public List<ResultDTO> getResultsByUserId(Long userId) {
        List<Result> results = resultRepository.findByUserId(userId);
        return results.stream()
                .map(result -> {
                    ResultDTO dto = modelMapper.map(result, ResultDTO.class);
                    dto.setAnswers(result.getAnswers().stream()
                            .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ResultDTO> getResultsByExamId(Long examId) {
        List<Result> results = resultRepository.findByExamId(examId);
        return results.stream()
                .map(result -> {
                    ResultDTO dto = modelMapper.map(result, ResultDTO.class);
                    dto.setAnswers(result.getAnswers().stream()
                            .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ResultDTO updateResult(Long id, ResultDTO resultDTO) {
        Result existingResult = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found with ID: " + id));

        // Update score and timestamp if needed
        existingResult.setScore(resultDTO.getScore());
        existingResult.setSubmittedAt(LocalDateTime.now());

        Result updated = resultRepository.save(existingResult);
        ResultDTO dto = modelMapper.map(updated, ResultDTO.class);
        dto.setAnswers(updated.getAnswers().stream()
                .map(a -> modelMapper.map(a, AnswerDTO.class))
                .collect(Collectors.toList()));
        return dto;
    }

    @Override
    public void deleteResult(Long id) {
        if (!resultRepository.existsById(id)) {
            throw new RuntimeException("Result not found with ID: " + id);
        }
        resultRepository.deleteById(id);
    }

    @Override
    public int calculateScore(Long resultId) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new RuntimeException("Result not found with ID: " + resultId));
        return (int) result.getAnswers().stream().filter(Answer::isCorrect).count();
    }
}
