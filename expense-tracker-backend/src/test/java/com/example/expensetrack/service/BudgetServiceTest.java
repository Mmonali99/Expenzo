package com.example.expensetrack.service;

import com.example.expensetrack.dto.BudgetDTO;
import com.example.expensetrack.dto.CategoryDTO;
import com.example.expensetrack.dto.UserDTO;
import com.example.expensetrack.exception.BudgetNotFoundException;
import com.example.expensetrack.model.Budget;
import com.example.expensetrack.model.Category;
import com.example.expensetrack.model.User;
import com.example.expensetrack.repository.BudgetRepository;
import com.example.expensetrack.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BudgetServiceTest {

    @Autowired
    private BudgetService budgetService;

    @MockBean
    private BudgetRepository budgetRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    private Budget budget;
    private BudgetDTO budgetDTO;

    @BeforeEach
    public void setup() {
        User user = new User(1L, "testuser", "password", "1234567890");
        budget = new Budget(1L, 1000.0, user, Collections.emptySet());
        budgetDTO = new BudgetDTO(1L, 1000.0, new UserDTO(1L, "testuser", "1234567890"), Collections.emptyList());
    }

    @Test
    public void testGetAllBudgets() {
        when(budgetRepository.findAll()).thenReturn(Collections.singletonList(budget));
        assertFalse(budgetService.getAllBudgets().isEmpty());
    }

    @Test
    public void testGetBudgetById() {
        when(budgetRepository.findById(1L)).thenReturn(Optional.of(budget));
        BudgetDTO found = budgetService.getBudgetById(1L);
        assertEquals(budget.getBudgetId(), found.getBudgetId());
    }

    @Test
    public void testGetBudgetById_NotFound() {
        when(budgetRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BudgetNotFoundException.class, () -> budgetService.getBudgetById(1L));
    }

    @Test
    public void testCreateBudget() {
        when(budgetRepository.save(any(Budget.class))).thenReturn(budget);
        BudgetDTO created = budgetService.createBudget(budgetDTO);
        assertEquals(budget.getBudgetId(), created.getBudgetId());
    }

    @Test
    public void testUpdateBudget() {
        when(budgetRepository.save(any(Budget.class))).thenReturn(budget);
        BudgetDTO updated = budgetService.updateBudget(1L, budgetDTO);
        assertEquals(budget.getBudgetId(), updated.getBudgetId());
    }

    @Test
    public void testDeleteBudget() {
        doNothing().when(budgetRepository).deleteById(1L);
        budgetService.deleteBudget(1L);
        verify(budgetRepository, times(1)).deleteById(1L);
    }
}
