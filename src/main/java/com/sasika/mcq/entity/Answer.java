package com.sasika.mcq.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "result_id")
    private Result result;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String selectedOption;
    private boolean isCorrect;

}
