package com.example.expensetrack.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyDTO {
    private Long monthlyId;
    private double monthlyPercentage;
    private double monthlyAmount;
    private double spentPercentage;
    private double spentAmount;
    private double remainingPercentage;
    private double remainingAmount;
    private CategoryDTO category;
}
