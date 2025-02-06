package com.example.expensetrack.service;

import com.example.expensetrack.dto.CategoryDTO;
import com.example.expensetrack.dto.MonthlyDTO;
import com.example.expensetrack.dto.WeeklyDTO;
import com.example.expensetrack.model.Category;
import com.example.expensetrack.model.Monthly;
import com.example.expensetrack.model.Weekly;
import com.example.expensetrack.repository.WeeklyRepository;
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
public class WeeklyServiceTest {

    @Autowired
    private WeeklyService weeklyService;

    @MockBean
    private WeeklyRepository weeklyRepository;

    private Weekly weekly;
    private WeeklyDTO weeklyDTO;
    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    public void setup() {
    	
    	category = new Category(1, "Utilities");
        categoryDTO = new CategoryDTO(1, "Utilities");
    	
        weekly = new Weekly(1L, 1, 10.0, 100.0, 5.0, 50.0, 5.0, 50.0, category);
        weeklyDTO = new WeeklyDTO(1L, 1, 10.0, 100.0, 5.0, 50.0, 5.0, 50.0, categoryDTO);
    }

    @Test
    public void testGetAllWeeklyDetails() {
        when(weeklyRepository.findAll()).thenReturn(Collections.singletonList(weekly));
        assertFalse(weeklyService.getAllWeeklyDetails().isEmpty());
    }

    @Test
    public void testGetWeeklyDetailById() {
        when(weeklyRepository.findById(1L)).thenReturn(Optional.of(weekly));
        WeeklyDTO found = weeklyService.getWeeklyDetailById(1L);
        assertEquals(weekly.getWeeklyId(), found.getWeeklyId());
    }

    @Test
    public void testCreateWeeklyDetail() {
        when(weeklyRepository.save(any(Weekly.class))).thenReturn(weekly);
        WeeklyDTO created = weeklyService.createWeeklyDetail(weeklyDTO);
        assertEquals(weekly.getWeeklyId(), created.getWeeklyId());
    }

    @Test
    public void testUpdateWeeklyDetail() {
        when(weeklyRepository.save(any(Weekly.class))).thenReturn(weekly);
        WeeklyDTO updated = weeklyService.updateWeeklyDetail(1L, weeklyDTO);
        assertEquals(weekly.getWeeklyId(), updated.getWeeklyId());
    }

    @Test
    public void testDeleteWeeklyDetail() {
        doNothing().when(weeklyRepository).deleteById(1L);
        weeklyService.deleteWeeklyDetail(1L);
        verify(weeklyRepository, times(1)).deleteById(1L);
    }
}
