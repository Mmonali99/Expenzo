package com.example.expensetrack.repository;

import com.example.expensetrack.model.Weekly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeeklyRepository extends JpaRepository<Weekly, Long> {
    List<Weekly> findByCategoryCategoryId(Integer categoryId);
}
