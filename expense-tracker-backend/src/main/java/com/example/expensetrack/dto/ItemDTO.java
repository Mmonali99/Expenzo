package com.example.expensetrack.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private Long itemId;
    private String itemName;
    private Double itemAmount;
    private ExpenseDTO expense;
    private CategoryDTO category;

    // Add this constructor
    public ItemDTO(Long itemId, String itemName, Double itemAmount) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemAmount = itemAmount;
    }
}
