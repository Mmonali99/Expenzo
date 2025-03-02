package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByDateBetween(LocalDate start, LocalDate end);
    List<Expense> findByAmountBetween(Double min, Double max);
    List<Expense> findByPurchaseLocationContaining(String location);

    @Query("SELECT SUM(i.amount) FROM Expense e JOIN e.items i WHERE e.user.username = :username AND e.date BETWEEN :start AND :end AND i.category IN :categories")
    Double calculateSpentAmount(
            @Param("username") String username,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("categories") List<String> categories);
}