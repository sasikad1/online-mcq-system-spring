package com.sasika.mcq.service.impl;

import com.sasika.mcq.dto.ExamDTO;
import com.sasika.mcq.entity.Exam;
import com.sasika.mcq.repo.ExamRepository;
import com.sasika.mcq.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ExamDTO> getAllExams() {
        return examRepository.findAll()
                .stream()
                .map(exam -> modelMapper.map(exam, ExamDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ExamDTO getExamById(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Exam not found with Id"+id));
        return modelMapper.map(exam, ExamDTO.class);
    }

    @Override
    public ExamDTO createExam(ExamDTO examDTO) {
        Exam exam = examRepository.save(modelMapper.map(examDTO, Exam.class));
        return modelMapper.map(exam, ExamDTO.class);
    }

    @Override
    public ExamDTO updateExam(Long id, ExamDTO examDTO) {
        Exam existExam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with Id " + id));

        existExam.setTitle(examDTO.getTitle());
        existExam.setDescription(examDTO.getDescription());
        existExam.setDuration(examDTO.getDuration());

        Exam updatedExam = examRepository.save(existExam);
        return modelMapper.map(updatedExam, ExamDTO.class);
    }


    @Override
    public void deleteExam(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + id));
        examRepository.delete(exam);
    }
}
