package com.expensetracker.controller;

import com.expensetracker.dto.BudgetDTO;
import com.expensetracker.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<BudgetDTO> createBudget(@Valid @RequestBody BudgetDTO budgetDTO, Principal principal) {
        BudgetDTO createdBudget = budgetService.createBudget(budgetDTO, principal.getName());
        return ResponseEntity.ok(createdBudget);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetDTO> getBudget(@PathVariable Long id, Principal principal) {
        BudgetDTO budgetDTO = budgetService.getBudgetById(id, principal.getName());
        return ResponseEntity.ok(budgetDTO);
    }

    @GetMapping("/{id}/with-spent")
    public ResponseEntity<BudgetDTO> getBudgetWithSpent(@PathVariable Long id, Principal principal) {
        BudgetDTO budgetDTO = budgetService.getBudgetWithSpent(id, principal.getName());
        return ResponseEntity.ok(budgetDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetDTO> updateBudget(@PathVariable Long id, @Valid @RequestBody BudgetDTO budgetDTO, Principal principal) {
        BudgetDTO updatedBudget = budgetService.updateBudget(id, budgetDTO, principal.getName());
        return ResponseEntity.ok(updatedBudget);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBudget(@PathVariable Long id, Principal principal) {
        budgetService.deleteBudget(id, principal.getName());
        return ResponseEntity.ok("Budget deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<List<BudgetDTO>> getAllBudgets(Principal principal) {
        List<BudgetDTO> budgets = budgetService.getAllBudgets(principal.getName());
        return ResponseEntity.ok(budgets);
    }

    @PostMapping("/{budgetId}/categories/{categoryName}")
    public ResponseEntity<BudgetDTO> addCategoryToBudget(
            @PathVariable Long budgetId,
            @PathVariable String categoryName,
            @RequestParam Double allocatedAmount, // Changed to Double to match service
            Principal principal) {
        BudgetDTO updatedBudget = budgetService.addCategoryToBudget(budgetId, categoryName, allocatedAmount, principal.getName());
        return ResponseEntity.ok(updatedBudget);
    }

    @PostMapping("/{budgetId}/calculate")
    public ResponseEntity<BudgetDTO> calculateBudgetValues(@PathVariable Long budgetId, Principal principal) {
        BudgetDTO calculatedBudget = budgetService.calculateBudgetValues(budgetId, principal.getName());
        return ResponseEntity.ok(calculatedBudget);
    }
}