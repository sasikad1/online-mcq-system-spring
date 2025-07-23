package com.sasika.mcq.service.impl;

import com.sasika.mcq.dto.QuestionDTO;
import com.sasika.mcq.entity.Exam;
import com.sasika.mcq.entity.Question;
import com.sasika.mcq.repo.ExamRepository;
import com.sasika.mcq.repo.QuestionRepository;
import com.sasika.mcq.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;
    private final ModelMapper modelMapper;

    public QuestionServiceImpl(QuestionRepository questionRepository,
                               ExamRepository examRepository,
                               ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.examRepository = examRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public QuestionDTO createQuestion(Long examId, QuestionDTO questionDTO) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        List<Question> existingQuestions = questionRepository.findByExamId(examId);

        if (existingQuestions.size() >= 5) {
            // Max 5 questions per exam
            throw new IllegalStateException("Maximum number of questions reached for this exam.");
        }

        Question question = modelMapper.map(questionDTO, Question.class);
        question.setExam(exam);

        Question savedQuestion = questionRepository.save(question);
        return modelMapper.map(savedQuestion, QuestionDTO.class);
    }


    @Override
    public List<QuestionDTO> getQuestionsByExamId(Long examId) {
        List<Question> questions = questionRepository.findByExamId(examId);
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public QuestionDTO getQuestionById(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));
        return modelMapper.map(question, QuestionDTO.class);
    }

    @Override
    public QuestionDTO updateQuestion(Long questionId, QuestionDTO questionDTO) {
        Question existingQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));

        existingQuestion.setQuestionText(questionDTO.getQuestionText());
        existingQuestion.setOptions(questionDTO.getOptions());
        existingQuestion.setCorrectOption(questionDTO.getCorrectOption());

        Question updatedQuestion = questionRepository.save(existingQuestion);
        return modelMapper.map(updatedQuestion, QuestionDTO.class);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new RuntimeException("Question not found with ID: " + questionId);
        }
        questionRepository.deleteById(questionId);
    }
}
