package com.sasika.mcq.dto;

import lombok.Data;

@Data
public class AnswerDTO {
    private Long id;
    private Long resultId;
    private Long questionId;
    private String selectedOption;
    private boolean isCorrect;

}