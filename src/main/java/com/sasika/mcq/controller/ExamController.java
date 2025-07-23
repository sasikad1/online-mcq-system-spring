package com.sasika.mcq.controller;

import com.sasika.mcq.dto.ExamDTO;
import com.sasika.mcq.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ExamController {
    private final ExamService examService;

    @GetMapping
    public ResponseEntity<List<ExamDTO>> getAllExams(){
        return ResponseEntity.ok(examService.getAllExams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> getExamById(@PathVariable Long id){
        return ResponseEntity.ok(examService.getExamById(id));
    }

    @PostMapping()
    public ResponseEntity<ExamDTO> createExam(@RequestBody ExamDTO examDTO){
        System.out.println("Sasaaaaaaaaaaaaaaaaaaaaaa");
        ExamDTO created = examService.createExam(examDTO);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable Long id, @RequestBody ExamDTO examDTO) {
        System.out.println("updateExam");
        System.out.println(examDTO.getTitle());
        return ResponseEntity.ok(examService.updateExam(id, examDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }
}
