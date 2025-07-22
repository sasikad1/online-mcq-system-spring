package com.sasika.mcq.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int duration;

    @OneToMany(mappedBy = "exam")
    @JsonIgnore
    private List<Result> results;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Question> questions;
}
