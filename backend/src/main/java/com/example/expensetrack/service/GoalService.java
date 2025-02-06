package com.example.expensetrack.service;

import com.example.expensetrack.dto.GoalDTO;
import com.example.expensetrack.dto.UserDTO;
import com.example.expensetrack.model.Goal;
import com.example.expensetrack.model.User;
import com.example.expensetrack.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public List<GoalDTO> getAllGoals() {
        return goalRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public GoalDTO getGoalById(Long goalId) {
        return goalRepository.findById(goalId).map(this::convertToDTO).orElse(null);
    }

    public GoalDTO createGoal(GoalDTO goalDTO) {
        Goal goal = convertToEntity(goalDTO);
        Goal savedGoal = goalRepository.save(goal);
        return convertToDTO(savedGoal);
    }

    public GoalDTO updateGoal(Long goalId, GoalDTO goalDTO) {
        Goal goal = convertToEntity(goalDTO);
        goal.setGoalId(goalId);
        Goal updatedGoal = goalRepository.save(goal);
        return convertToDTO(updatedGoal);
    }

    public void deleteGoal(Long goalId) {
        goalRepository.deleteById(goalId);
    }

    public List<GoalDTO> getGoalsByUserId(Long userId) {
        return goalRepository.findByUserUserId(userId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private GoalDTO convertToDTO(Goal goal) {
        GoalDTO goalDTO = new GoalDTO();
        goalDTO.setGoalId(goal.getGoalId());
        goalDTO.setTitle(goal.getTitle());
        goalDTO.setDescription(goal.getDescription());
        goalDTO.setGoalType(goal.getGoalType());
        goalDTO.setGoalAmount(goal.getGoalAmount());
        goalDTO.setTargetDate(goal.getTargetDate());
        goalDTO.setProgress(goal.getProgress());
        goalDTO.setUser(new UserDTO(goal.getUser().getUserId(), goal.getUser().getUsername(), goal.getUser().getPhoneNumber()));
        return goalDTO;
    }

    private Goal convertToEntity(GoalDTO goalDTO) {
        Goal goal = new Goal();
        goal.setGoalId(goalDTO.getGoalId());
        goal.setTitle(goalDTO.getTitle());
        goal.setDescription(goalDTO.getDescription());
        goal.setGoalType(goalDTO.getGoalType());
        goal.setGoalAmount(goalDTO.getGoalAmount());
        goal.setTargetDate(goalDTO.getTargetDate());
        goal.setProgress(goalDTO.getProgress());
        goal.setUser(new User(goalDTO.getUser().getUserId(), goalDTO.getUser().getUsername(), goalDTO.getUser().getPassword(), goalDTO.getUser().getPhoneNumber()));
        return goal;
    }
}
