package com.example.expensetrack.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyDTO {
    private Long weeklyId;
    private int weekNumber;
    private double weeklyPercentage;
    private double weeklyAmount;
    private double spentPercentage;
    private double spentAmount;
    private double remainingPercentage;
    private double remainingAmount;
    private CategoryDTO category;
}
