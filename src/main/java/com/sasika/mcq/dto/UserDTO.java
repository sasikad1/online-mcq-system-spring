package com.sasika.mcq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
}
