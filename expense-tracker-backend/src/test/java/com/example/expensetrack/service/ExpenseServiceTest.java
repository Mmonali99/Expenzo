package com.example.expensetrack.service;

import com.example.expensetrack.dto.ExpenseDTO;
import com.example.expensetrack.dto.UserDTO;
import com.example.expensetrack.model.Expense;
import com.example.expensetrack.model.User;
import com.example.expensetrack.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ExpenseServiceTest {

    @Autowired
    private ExpenseService expenseService;

    @MockBean
    private ExpenseRepository expenseRepository;

    private Expense expense;
    private ExpenseDTO expenseDTO;

    @BeforeEach
    public void setup() {
        User user = new User(1L, "testuser", "password", "1234567890");
        expense = new Expense(1L, LocalDate.now(), LocalTime.now(), 100.0, "Test", "Store", user, null);
        expenseDTO = new ExpenseDTO(1L, LocalDate.now(), LocalTime.now(), 100.0, "Test", "Store", new UserDTO(1L, "testuser", "1234567890"));
    }

    @Test
    public void testGetAllExpenses() {
        when(expenseRepository.findAll()).thenReturn(Collections.singletonList(expense));
        assertFalse(expenseService.getAllExpenses().isEmpty());
    }

    @Test
    public void testGetExpenseById() {
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        ExpenseDTO found = expenseService.getExpenseById(1L);
        assertEquals(expense.getExpenseId(), found.getExpenseId());
    }

    @Test
    public void testCreateExpense() {
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        ExpenseDTO created = expenseService.createExpense(expenseDTO);
        assertEquals(expense.getExpenseId(), created.getExpenseId());
    }

    @Test
    public void testUpdateExpense() {
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        ExpenseDTO updated = expenseService.updateExpense(1L, expenseDTO);
        assertEquals(expense.getExpenseId(), updated.getExpenseId());
    }

    @Test
    public void testDeleteExpense() {
        doNothing().when(expenseRepository).deleteById(1L);
        expenseService.deleteExpense(1L);
        verify(expenseRepository, times(1)).deleteById(1L);
    }
}
