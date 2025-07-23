package com.sasika.mcq.controller;

import com.sasika.mcq.dto.AnswerDTO;
import com.sasika.mcq.service.AnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
@CrossOrigin("*")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity<AnswerDTO> createAnswer(@RequestBody AnswerDTO answerDTO) {
        return ResponseEntity.ok(answerService.createAnswer(answerDTO));
    }

    @GetMapping("/by-question-and-user")
    public ResponseEntity<List<AnswerDTO>> getAnswersByQuestionAndUser(
            @RequestParam Long questionId,
            @RequestParam Long userId
    ) {
        List<AnswerDTO> answers = answerService.getAnswersByQuestionAndUser(questionId, userId);
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/by-user-and-exam")
    public ResponseEntity<List<AnswerDTO>> getAnswersByUserAndExam(
            @RequestParam Long userId,
            @RequestParam Long examId
    ) {
        List<AnswerDTO> answers = answerService.getAnswersByUserAndExam(userId, examId);
        return ResponseEntity.ok(answers);
    }

    /////////////////////////////////////////////////



    @GetMapping("/{id}")
    public ResponseEntity<AnswerDTO> getAnswerById(@PathVariable Long id) {
        return ResponseEntity.ok(answerService.getAnswerById(id));
    }

    @GetMapping("/result/{resultId}")
    public ResponseEntity<List<AnswerDTO>> getAnswersByResultId(@PathVariable Long resultId) {
        return ResponseEntity.ok(answerService.getAnswersByResultId(resultId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnswerDTO> updateAnswer(@PathVariable Long id, @RequestBody AnswerDTO answerDTO) {
        return ResponseEntity.ok(answerService.updateAnswer(id, answerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }
}
