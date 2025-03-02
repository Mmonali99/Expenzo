package com.expensetracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDTO {
    private Long id;

    @NotNull(message = "Total budget amount is required")
    @Positive(message = "Total budget amount must be positive")
    private Double totalBudgetAmount;

    @NotNull(message = "Month is required")
    private Integer month;

    @NotNull(message = "Year is required")
    private Integer year;

    private List<BudgetCategoryDTO> budgetCategories; // Updated to use BudgetCategoryDTO
    private Double spent;
    
    
}