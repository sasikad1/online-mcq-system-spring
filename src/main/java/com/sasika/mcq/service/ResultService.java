package com.sasika.mcq.service;

import com.sasika.mcq.dto.ResultDTO;

import java.util.List;

public interface ResultService {

    List<ResultDTO> findAllResults();

    // Create a new result (after an exam submission)
    ResultDTO createResult(ResultDTO resultDTO);

    // Get a result by its ID
    ResultDTO getResultById(Long id);

    // Get all results for a specific user
    List<ResultDTO> getResultsByUserId(Long userId);

    // Get all results for a specific exam
    List<ResultDTO> getResultsByExamId(Long examId);

    // Update a result
    ResultDTO updateResult(Long id, ResultDTO resultDTO);

    // Delete a result
    void deleteResult(Long id);

    // Optional: Calculate score (if not done client-side)
    int calculateScore(Long resultId);
}
