package com.example.expensetrack.repository;

import com.example.expensetrack.model.Monthly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyRepository extends JpaRepository<Monthly, Long> {
    List<Monthly> findByCategoryCategoryId(Integer categoryId);
}
