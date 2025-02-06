package com.example.expensetrack.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {

    private Long expenseId;
    private LocalDate date;
    private LocalTime time;
    private Double expenseAmount;
    private String description;
    private String purchaseLocation;
    private UserDTO user;
    private List<ItemDTO> items;

    // Additional constructor for the test case without items list
    public ExpenseDTO(Long expenseId, LocalDate date, LocalTime time, Double expenseAmount, String description, String purchaseLocation, UserDTO user) {
        this.expenseId = expenseId;
        this.date = date;
        this.time = time;
        this.expenseAmount = expenseAmount;
        this.description = description;
        this.purchaseLocation = purchaseLocation;
        this.user = user;
    }
}
