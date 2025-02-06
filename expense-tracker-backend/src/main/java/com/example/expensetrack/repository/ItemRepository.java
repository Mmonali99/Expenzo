package com.example.expensetrack.repository;

import com.example.expensetrack.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByExpenseExpenseId(Long expenseId); // Use full path of the field name
    List<Item> findByCategoryCategoryId(Integer categoryId); // Already correct
}
