package com.sasika.mcq.dto;

import lombok.Data;

@Data
public class ExamDTO {
    private Long id;
    private String title;
    private String description;
    private int duration;
}
