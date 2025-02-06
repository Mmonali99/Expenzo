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
public class UserDTO {
    private Long userId;
    private String username;
    private String password; // Field for user password
    private String newPassword; // Field for new password (used in reset scenarios)
    private String phoneNumber;
    private List<ExpenseDTO> expenses;
    private List<BudgetDTO> budgets;
    private List<GoalDTO> goals;

    /**
     * Constructor with all fields, including newPassword for reset scenarios.
     *
     * @param userId      the user ID
     * @param username    the username
     * @param password    the user password
     * @param newPassword the new password
     * @param phoneNumber the phone number
     */
    public UserDTO(Long userId, String username, String password, String newPassword, String phoneNumber) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.newPassword = newPassword;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructor without userId and newPassword (for creating new users).
     *
     * @param username    the username
     * @param password    the user password
     * @param phoneNumber the phone number
     */
    public UserDTO(String username, String password, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructor without password and newPassword (for read-only purposes).
     *
     * @param userId      the user ID
     * @param username    the username
     * @param phoneNumber the phone number
     */
    public UserDTO(Long userId, String username, String phoneNumber) {
        this.userId = userId;
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructor with only username and phoneNumber (minimal information).
     *
     * @param username    the username
     * @param phoneNumber the phone number
     */
    public UserDTO(String username, String phoneNumber) {
        this.username = username;
        this.phoneNumber = phoneNumber;
    }
}
