package com.example.expensetrack.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private int categoryId;
    private String categoryName;
    private CategoryTypeDTO categoryType;
    private BudgetDTO budget;
    private List<ItemDTO> items;
    private List<WeeklyDTO> weeklyDetails;
    private List<MonthlyDTO> monthlyDetails;
    private UserDTO user;

    public CategoryDTO(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
