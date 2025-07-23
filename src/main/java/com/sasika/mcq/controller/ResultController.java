package com.sasika.mcq.controller;

import com.sasika.mcq.dto.ResultDTO;
import com.sasika.mcq.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping
    public ResponseEntity<List<ResultDTO>> findAllResults() {
        List<ResultDTO> resultDTOs = resultService.findAllResults();
        return ResponseEntity.ok(resultDTOs);
    }

    // Create a new Result (submit exam)
    @PostMapping
    public ResponseEntity<ResultDTO> createResult(@RequestBody ResultDTO resultDTO) {
        ResultDTO createdResult = resultService.createResult(resultDTO);
        return ResponseEntity.ok(createdResult);
    }

    // Get Result by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO> getResultById(@PathVariable Long id) {
        ResultDTO resultDTO = resultService.getResultById(id);
        return ResponseEntity.ok(resultDTO);
    }

    // Get all Results by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ResultDTO>> getResultsByUserId(@PathVariable Long userId) {
        List<ResultDTO> results = resultService.getResultsByUserId(userId);
        return ResponseEntity.ok(results);
    }

    // Get all Results by Exam ID
    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<ResultDTO>> getResultsByExamId(@PathVariable Long examId) {
        List<ResultDTO> results = resultService.getResultsByExamId(examId);
        return ResponseEntity.ok(results);
    }

    // Update a Result by ID
    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO> updateResult(@PathVariable Long id, @RequestBody ResultDTO resultDTO) {
        ResultDTO updatedResult = resultService.updateResult(id, resultDTO);
        return ResponseEntity.ok(updatedResult);
    }

    // Delete a Result by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
        return ResponseEntity.noContent().build();
    }

    // Calculate score for a given Result ID
    @GetMapping("/{id}/score")
    public ResponseEntity<Integer> calculateScore(@PathVariable Long id) {
        int score = resultService.calculateScore(id);
        return ResponseEntity.ok(score);
    }
}
