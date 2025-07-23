package com.sasika.mcq.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Result> results;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Answer> answers;
}
