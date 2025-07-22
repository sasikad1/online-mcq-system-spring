package com.sasika.mcq.service;

import com.sasika.mcq.dto.ExamDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamService {
    List<ExamDTO> getAllExams();
    ExamDTO getExamById(Long id);
    ExamDTO createExam(ExamDTO examDTO);
    ExamDTO updateExam(Long id, ExamDTO examDTO);
    void deleteExam(Long id);
}
