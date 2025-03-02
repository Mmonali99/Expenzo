package com.expensetracker.controller;

import com.expensetracker.dto.GoalDTO;
import com.expensetracker.service.GoalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping
    public ResponseEntity<GoalDTO> createGoal(@Valid @RequestBody GoalDTO goalDTO, Principal principal) {
        GoalDTO createdGoal = goalService.createGoal(goalDTO, principal.getName());
        return ResponseEntity.ok(createdGoal);
    }

    @GetMapping
    public ResponseEntity<List<GoalDTO>> getAllGoals(Principal principal) {
        List<GoalDTO> goals = goalService.getAllGoals(principal.getName());
        return ResponseEntity.ok(goals);
    }

    @PutMapping("/{goalId}")
    public ResponseEntity<GoalDTO> updateGoal(@PathVariable Long goalId, @Valid @RequestBody GoalDTO goalDTO, Principal principal) {
        GoalDTO updatedGoal = goalService.updateGoal(goalId, goalDTO, principal.getName());
        return ResponseEntity.ok(updatedGoal);
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<String> deleteGoal(@PathVariable Long goalId, Principal principal) {
        goalService.deleteGoal(goalId, principal.getName());
        return ResponseEntity.ok("Goal deleted successfully.");
    }
}