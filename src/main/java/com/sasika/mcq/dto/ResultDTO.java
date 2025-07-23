package com.sasika.mcq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {
    private Long id;
    private Long userId;
    private Long examId;
    private int score;
    private LocalDateTime submittedAt;
    private List<AnswerDTO> answers;
    private String userName;
    private String examName;
    private List<Long> questionIds; // ✅ Add this
    private List<String> correctQuestions;
    private List<String> incorrectQuestions;
}
