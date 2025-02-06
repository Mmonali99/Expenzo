package com.example.expensetrack.repository;

import com.example.expensetrack.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByBudgetBudgetId(Long budgetId);
    List<Category> findByUserUserId(Long userId);
}
