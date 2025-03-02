package com.expensetracker.service;

import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.dto.ItemDTO;
import com.expensetracker.model.Expense;
import com.expensetracker.model.Item;
import com.expensetracker.model.User;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.UserRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ExpenseDTO addExpense(ExpenseDTO expenseDTO, String username) {
        logger.info("Adding expense for user: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Expense expense = new Expense(
                expenseDTO.getAmount(),
                expenseDTO.getPurchaseLocation(),
                expenseDTO.getDate(),
                expenseDTO.getTime(),
                expenseDTO.getDescription(),
                convertToItemList(expenseDTO.getItems()),
                user
        );
        Expense savedExpense = expenseRepository.save(expense);
        logger.info("Expense added with ID: {}", savedExpense.getId());
        return convertToDTO(savedExpense);
    }

    @Transactional(readOnly = true)
    public List<ExpenseDTO> getExpensesByUser(String username) {
        logger.debug("Fetching expenses for user: {}", username);
        return expenseRepository.findAll().stream()
                .filter(expense -> expense.getUser().getUsername().equals(username))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ExpenseDTO getExpenseById(Long id, String username) {
        logger.debug("Fetching expense ID: {} for user: {}", id, username);
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found"));
        if (!expense.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Unauthorized access to expense");
        }
        Hibernate.initialize(expense.getItems()); // Handle lazy loading
        return convertToDTO(expense);
    }

    @Transactional
    public ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDTO, String username) {
        logger.info("Updating expense ID: {} for user: {}", id, username);
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found"));
        if (!expense.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Unauthorized access to expense");
        }
        expense.setAmount(expenseDTO.getAmount());
        expense.setPurchaseLocation(expenseDTO.getPurchaseLocation());
        expense.setDate(expenseDTO.getDate());
        expense.setTime(expenseDTO.getTime());
        expense.setDescription(expenseDTO.getDescription());
        expense.setItems(convertToItemList(expenseDTO.getItems()));
        Expense updatedExpense = expenseRepository.save(expense);
        logger.info("Expense updated successfully: {}", id);
        return convertToDTO(updatedExpense);
    }

    @Transactional
    public void deleteExpense(Long id, String username) {
        logger.info("Deleting expense ID: {} for user: {}", id, username);
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found"));
        if (!expense.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Unauthorized access to expense");
        }
        expenseRepository.deleteById(id);
        logger.info("Expense deleted successfully: {}", id);
    }

    @Transactional(readOnly = true)
    public List<ExpenseDTO> filterExpenses(LocalDate startDate, LocalDate endDate, String category, Double minAmount, Double maxAmount, String username) {
        logger.debug("Filtering expenses for user: {}", username);
        return expenseRepository.findByDateBetween(startDate != null ? startDate : LocalDate.MIN, endDate != null ? endDate : LocalDate.MAX)
                .stream()
                .filter(expense -> expense.getUser().getUsername().equals(username))
                .filter(expense -> category == null || expense.getItems().stream().anyMatch(item -> item.getCategory().equalsIgnoreCase(category)))
                .filter(expense -> minAmount == null || expense.getAmount() >= minAmount)
                .filter(expense -> maxAmount == null || expense.getAmount() <= maxAmount)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private List<Item> convertToItemList(List<ItemDTO> itemDTOs) {
        if (itemDTOs == null) return null;
        return itemDTOs.stream()
                .map(dto -> new Item(dto.getName(), dto.getCategory(), dto.getAmount(), null))
                .collect(Collectors.toList());
    }

    private ExpenseDTO convertToDTO(Expense expense) {
        return new ExpenseDTO(
                expense.getAmount(),
                expense.getPurchaseLocation(),
                expense.getDate(),
                expense.getTime(),
                expense.getDescription(),
                convertToItemDTOList(expense.getItems())
        );
    }

    private List<ItemDTO> convertToItemDTOList(List<Item> items) {
        if (items == null) return null;
        return items.stream()
                .map(item -> new ItemDTO(item.getName(), item.getCategory(), item.getAmount()))
                .collect(Collectors.toList());
    }
}