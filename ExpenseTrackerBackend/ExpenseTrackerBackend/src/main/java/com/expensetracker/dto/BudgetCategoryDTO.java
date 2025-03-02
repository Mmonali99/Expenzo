package com.expensetracker.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetCategoryDTO {
    private Long id;
    private CategoryDTO category;
    private Double allocatedAmount;
}