package com.expensetracker.utils;

import com.expensetracker.dto.ExpenseDTO;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class ValidationUtil {

    public static boolean isValidExpense(ExpenseDTO expenseDTO) {
        if (expenseDTO == null) {
            return false;
        }

        return expenseDTO.getAmount() != null && expenseDTO.getAmount() > 0
                && StringUtils.hasText(expenseDTO.getPurchaseLocation())
                && expenseDTO.getDate() != null
                && expenseDTO.getTime() != null;
    }

    public static boolean isValidDateRange(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            return false;
        }
        return !start.isAfter(end);
    }
}