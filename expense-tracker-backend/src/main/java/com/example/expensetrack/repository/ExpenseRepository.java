package com.example.expensetrack.repository;

import com.example.expensetrack.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserUserId(Long userId);
    List<Expense> findByDateBetweenAndUserUserId(LocalDate startDate, LocalDate endDate, Long userId);
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Expense> findByExpenseAmountBetween(Double minAmount, Double maxAmount);
}
