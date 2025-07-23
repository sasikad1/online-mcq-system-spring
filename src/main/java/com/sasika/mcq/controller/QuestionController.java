package com.sasika.mcq.controller;

import com.sasika.mcq.dto.QuestionDTO;
import com.sasika.mcq.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin("*")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/{examId}")
    public ResponseEntity<QuestionDTO> createQuestion(@PathVariable Long examId, @RequestBody QuestionDTO questionDTO) {
        QuestionDTO created = questionService.createQuestion(examId, questionDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByExamId(@PathVariable Long examId) {
        List<QuestionDTO> questions = questionService.getQuestionsByExamId(examId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long questionId) {
        QuestionDTO question = questionService.getQuestionById(questionId);
        return ResponseEntity.ok(question);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable Long questionId, @RequestBody QuestionDTO questionDTO) {
        QuestionDTO updated = questionService.updateQuestion(questionId, questionDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}
