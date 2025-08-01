package com.sasika.mcq.service.impl;

import com.sasika.mcq.dto.AnswerDTO;
import com.sasika.mcq.dto.ExamDTO;
import com.sasika.mcq.dto.ResultDTO;
import com.sasika.mcq.entity.*;
import com.sasika.mcq.repo.*;
import com.sasika.mcq.service.ResultService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<ResultDTO> findAllResults() {
        return resultRepository.findAll()
                .stream()
                .map(result -> {
                    ResultDTO dto = modelMapper.map(result, ResultDTO.class);

                    List<String> correctQuestions = new ArrayList<>();
                    List<String> incorrectQuestions = new ArrayList<>();

                    List<Answer> answers = result.getAnswers();
                    for(Answer answer : answers){
                        if(answer.isCorrect()){
                            correctQuestions.add(answer.getQuestion().getQuestionText());
                        }else{
                            incorrectQuestions.add(answer.getQuestion().getQuestionText());
                        }
                    }

                    dto.setCorrectQuestions(correctQuestions);
                    dto.setIncorrectQuestions(incorrectQuestions);
                    // Add user name
                    if (result.getUser() != null) {
                        dto.setUserId(result.getUser().getId());
                        dto.setUserName(result.getUser().getName()); // ✅ Set user name
                    }

                    // Optional: add exam name
                    if (result.getExam() != null) {
                        dto.setExamId(result.getExam().getId());
                        dto.setExamName(result.getExam().getTitle()); // ✅ Set exam name
                    }


                    return dto;
                })
                .collect(Collectors.toList());
    }


//
@Override
@Transactional
public ResultDTO createResult(ResultDTO resultDTO) {
    Result result = modelMapper.map(resultDTO, Result.class);

    List<Answer> answers = answerRepository.findByUserIdAndQuestionExamId(
            result.getUser().getId(), result.getExam().getId());

    int score = 0;
    List<Long> questionIds = new ArrayList<>();
    List<String> correctQuestions = new ArrayList<>();
    List<String> incorrectQuestions = new ArrayList<>();

    for (Answer answer : answers) {
        questionIds.add(answer.getQuestion().getId());
        if (answer.isCorrect()) {
            score++;
            correctQuestions.add(answer.getQuestion().getQuestionText()); // assuming `getQuestionText()`
        } else {
            incorrectQuestions.add(answer.getQuestion().getQuestionText());
        }
    }

    result.setScore(score);
    result.setSubmittedAt(LocalDateTime.now());
    result.setExam(answers.get(0).getQuestion().getExam());
    result.setUser(answers.get(0).getUser());
    result.setAnswers(answers);
    Result savedResult = resultRepository.save(result);

    // Update answers with result link
    for (Answer answer : answers) {
        answer.setResult(savedResult);
    }

    // Map result to DTO
    ResultDTO responseDTO = modelMapper.map(savedResult, ResultDTO.class);
    responseDTO.setUserId(savedResult.getUser().getId());
    responseDTO.setExamId(savedResult.getExam().getId());
    responseDTO.setUserName(savedResult.getUser().getName());
    responseDTO.setExamName(savedResult.getExam().getTitle()); // assuming exam has name
    responseDTO.setQuestionIds(questionIds);
    responseDTO.setCorrectQuestions(correctQuestions);
    responseDTO.setIncorrectQuestions(incorrectQuestions);

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
