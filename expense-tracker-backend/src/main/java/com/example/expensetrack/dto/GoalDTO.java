package com.example.expensetrack.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoalDTO {

    private Long goalId;
    private String title;
    private String description;
    private String goalType;
    private Double goalAmount;
    private LocalDate targetDate;
    private Integer progress;
    private UserDTO user;
}
