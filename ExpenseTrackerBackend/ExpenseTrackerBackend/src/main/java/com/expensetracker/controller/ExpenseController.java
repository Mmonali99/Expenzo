package com.expensetracker.controller;

import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> addExpense(@Valid @RequestBody ExpenseDTO expenseDTO, Principal principal) {
        System.out.println("Received expense request for user: " + principal.getName());
        ExpenseDTO savedExpense = expenseService.addExpense(expenseDTO, principal.getName());
        return ResponseEntity.ok(savedExpense);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses(Principal principal) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByUser(principal.getName());
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable Long id, Principal principal) {
        ExpenseDTO expense = expenseService.getExpenseById(id, principal.getName());
        return ResponseEntity.ok(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseDTO expenseDTO, Principal principal) {
        ExpenseDTO updatedExpense = expenseService.updateExpense(id, expenseDTO, principal.getName());
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id, Principal principal) {
        expenseService.deleteExpense(id, principal.getName());
        return ResponseEntity.ok("Expense deleted successfully");
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ExpenseDTO>> filterExpenses(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            Principal principal) {
        List<ExpenseDTO> filteredExpenses = expenseService.filterExpenses(startDate, endDate, category, minAmount, maxAmount, principal.getName());
        return ResponseEntity.ok(filteredExpenses);
    }
}