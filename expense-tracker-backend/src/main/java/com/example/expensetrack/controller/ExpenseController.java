package com.example.expensetrack.controller;

import com.example.expensetrack.dto.ExpenseDTO;
import com.example.expensetrack.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public List<ExpenseDTO> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{id}")
    public ExpenseDTO getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    @PostMapping
    public ExpenseDTO createExpense(@RequestBody ExpenseDTO expenseDTO) {
        return expenseService.createExpense(expenseDTO);
    }

    @PutMapping("/{id}")
    public ExpenseDTO updateExpense(@PathVariable Long id, @RequestBody ExpenseDTO expenseDTO) {
        return expenseService.updateExpense(id, expenseDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

    @GetMapping("/user/{userId}")
    public List<ExpenseDTO> getExpensesByUserId(@PathVariable Long userId) {
        return expenseService.getExpensesByUserId(userId);
    }

    @GetMapping("/date-range")
    public List<ExpenseDTO> getExpensesByDateRange(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        return expenseService.getExpensesByDateRange(startDate, endDate);
    }

    @GetMapping("/amount-range")
    public List<ExpenseDTO> getExpensesByAmountRange(
            @RequestParam("minAmount") Double minAmount,
            @RequestParam("maxAmount") Double maxAmount) {
        return expenseService.getExpensesByAmountRange(minAmount, maxAmount);
    }

    @GetMapping("/category")
    public List<ExpenseDTO> getExpensesByCategoryId(@RequestParam("categoryId") Integer categoryId) {
        return expenseService.getExpensesByCategoryId(categoryId);
    }
}
