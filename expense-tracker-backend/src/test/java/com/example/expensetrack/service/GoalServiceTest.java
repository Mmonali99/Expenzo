package com.example.expensetrack.service;

import com.example.expensetrack.dto.GoalDTO;
import com.example.expensetrack.dto.UserDTO;
import com.example.expensetrack.model.Goal;
import com.example.expensetrack.model.User;
import com.example.expensetrack.repository.GoalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GoalServiceTest {

    @Autowired
    private GoalService goalService;

    @MockBean
    private GoalRepository goalRepository;

    private Goal goal;
    private GoalDTO goalDTO;

    @BeforeEach
    public void setup() {
        User user = new User(1L, "testuser", "password", "1234567890");
        goal = new Goal(1L, "Save", "Save 1000", "Savings", 1000.0, LocalDate.now().plusMonths(6), 0, user);
        goalDTO = new GoalDTO(1L, "Save", "Save 1000", "Savings", 1000.0, LocalDate.now().plusMonths(6), 0, new UserDTO(1L, "testuser", "1234567890"));
    }

    @Test
    public void testGetAllGoals() {
        when(goalRepository.findAll()).thenReturn(Collections.singletonList(goal));
        assertFalse(goalService.getAllGoals().isEmpty());
    }

    @Test
    public void testGetGoalById() {
        when(goalRepository.findById(1L)).thenReturn(Optional.of(goal));
        GoalDTO found = goalService.getGoalById(1L);
        assertEquals(goal.getGoalId(), found.getGoalId());
    }

    @Test
    public void testCreateGoal() {
        when(goalRepository.save(any(Goal.class))).thenReturn(goal);
        GoalDTO created = goalService.createGoal(goalDTO);
        assertEquals(goal.getGoalId(), created.getGoalId());
    }

    @Test
    public void testUpdateGoal() {
        when(goalRepository.save(any(Goal.class))).thenReturn(goal);
        GoalDTO updated = goalService.updateGoal(1L, goalDTO);
        assertEquals(goal.getGoalId(), updated.getGoalId());
    }

    @Test
    public void testDeleteGoal() {
        doNothing().when(goalRepository).deleteById(1L);
        goalService.deleteGoal(1L);
        verify(goalRepository, times(1)).deleteById(1L);
    }
}
