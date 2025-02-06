package com.example.expensetrack.service;

import com.example.expensetrack.dto.ExpenseDTO;
import com.example.expensetrack.dto.UserDTO;
import com.example.expensetrack.model.Expense;
import com.example.expensetrack.model.Item;
import com.example.expensetrack.model.User;
import com.example.expensetrack.repository.ExpenseRepository;
import com.example.expensetrack.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ExpenseDTO getExpenseById(Long expenseId) {
        return expenseRepository.findById(expenseId).map(this::convertToDTO).orElse(null);
    }

    public ExpenseDTO createExpense(ExpenseDTO expenseDTO) {
        Expense expense = convertToEntity(expenseDTO);
        Expense savedExpense = expenseRepository.save(expense);
        return convertToDTO(savedExpense);
    }

    public ExpenseDTO updateExpense(Long expenseId, ExpenseDTO expenseDTO) {
        Expense expense = convertToEntity(expenseDTO);
        expense.setExpenseId(expenseId);
        Expense updatedExpense = expenseRepository.save(expense);
        return convertToDTO(updatedExpense);
    }

    public void deleteExpense(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }

    public List<ExpenseDTO> getExpensesByUserId(Long userId) {
        return expenseRepository.findByUserUserId(userId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ExpenseDTO> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByDateBetween(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> getExpensesByAmountRange(Double minAmount, Double maxAmount) {
        return expenseRepository.findByExpenseAmountBetween(minAmount, maxAmount).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> getExpensesByCategoryId(Integer categoryId) {
        return itemRepository.findByCategoryCategoryId(categoryId).stream()
                .map(Item::getExpense)
                .distinct()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ExpenseDTO convertToDTO(Expense expense) {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setExpenseId(expense.getExpenseId());
        expenseDTO.setDate(expense.getDate());
        expenseDTO.setTime(expense.getTime());
        expenseDTO.setExpenseAmount(expense.getExpenseAmount());
        expenseDTO.setDescription(expense.getDescription());
        expenseDTO.setPurchaseLocation(expense.getPurchaseLocation());
        expenseDTO.setUser(new UserDTO(expense.getUser().getUserId(), expense.getUser().getUsername(), expense.getUser().getPhoneNumber()));
        return expenseDTO;
    }

    private Expense convertToEntity(ExpenseDTO expenseDTO) {
        Expense expense = new Expense();
        expense.setExpenseId(expenseDTO.getExpenseId());
        expense.setDate(expenseDTO.getDate());
        expense.setTime(expenseDTO.getTime());
        expense.setExpenseAmount(expenseDTO.getExpenseAmount());
        expense.setDescription(expenseDTO.getDescription());
        expense.setPurchaseLocation(expenseDTO.getPurchaseLocation());
        expense.setUser(new User(expenseDTO.getUser().getUserId(), expenseDTO.getUser().getUsername(), expenseDTO.getUser().getPassword(), expenseDTO.getUser().getPhoneNumber()));
        return expense;
    }
}
