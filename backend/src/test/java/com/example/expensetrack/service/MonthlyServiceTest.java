package com.example.expensetrack.service;

import com.example.expensetrack.dto.CategoryDTO;
import com.example.expensetrack.dto.MonthlyDTO;
import com.example.expensetrack.model.Category;
import com.example.expensetrack.model.Monthly;
import com.example.expensetrack.repository.MonthlyRepository;
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
public class MonthlyServiceTest {

    @Autowired
    private MonthlyService monthlyService;

    @MockBean
    private MonthlyRepository monthlyRepository;

    private Monthly monthly;
    private MonthlyDTO monthlyDTO;
    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    public void setup() {
        category = new Category(1, "Utilities");
        categoryDTO = new CategoryDTO(1, "Utilities");

        monthly = new Monthly(1L, 50.0, 500.0, 25.0, 250.0, 25.0, 250.0, category);
        monthlyDTO = new MonthlyDTO(1L, 50.0, 500.0, 25.0, 250.0, 25.0, 250.0, categoryDTO);
    }

    @Test
    public void testGetAllMonthlyDetails() {
        when(monthlyRepository.findAll()).thenReturn(Collections.singletonList(monthly));
        assertFalse(monthlyService.getAllMonthlyDetails().isEmpty());
    }

    @Test
    public void testGetMonthlyDetailById() {
        when(monthlyRepository.findById(1L)).thenReturn(Optional.of(monthly));
        MonthlyDTO found = monthlyService.getMonthlyDetailById(1L);
        assertEquals(monthly.getMonthlyId(), found.getMonthlyId());
    }

    @Test
    public void testCreateMonthlyDetail() {
        when(monthlyRepository.save(any(Monthly.class))).thenReturn(monthly);
        MonthlyDTO created = monthlyService.createMonthlyDetail(monthlyDTO);
        assertEquals(monthly.getMonthlyId(), created.getMonthlyId());
    }

    @Test
    public void testUpdateMonthlyDetail() {
        when(monthlyRepository.save(any(Monthly.class))).thenReturn(monthly);
        MonthlyDTO updated = monthlyService.updateMonthlyDetail(1L, monthlyDTO);
        assertEquals(monthly.getMonthlyId(), updated.getMonthlyId());
    }

    @Test
    public void testDeleteMonthlyDetail() {
        doNothing().when(monthlyRepository).deleteById(1L);
        monthlyService.deleteMonthlyDetail(1L);
        verify(monthlyRepository, times(1)).deleteById(1L);
    }
}
