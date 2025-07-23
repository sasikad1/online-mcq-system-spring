package com.sasika.mcq.dto;

import com.sasika.mcq.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private List<Answer> answers;
}
