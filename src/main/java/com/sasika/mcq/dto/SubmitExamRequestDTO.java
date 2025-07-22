package com.sasika.mcq.dto;

import java.util.List;

public class SubmitExamRequestDTO {
    private Long userId;
    private Long examId;
    private List<AnswerSubmissionDTO> answers;
}

//Used to submit all answers for a specific exam attempt.