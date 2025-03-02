package com.expensetracker.service;

import com.expensetracker.dto.GoalDTO;
import com.expensetracker.model.Goal;
import com.expensetracker.model.User;
import com.expensetracker.repository.GoalRepository;
import com.expensetracker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalService {

    private static final Logger logger = LoggerFactory.getLogger(GoalService.class);

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public GoalService(GoalRepository goalRepository, UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public GoalDTO createGoal(GoalDTO goalDTO, String username) {
        logger.info("Creating goal for user: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Goal goal = new Goal(
                goalDTO.getName(),
                goalDTO.getTargetAmount(),
                goalDTO.getSavedAmount(),
                goalDTO.getDeadline(),
                user
        );
        Goal savedGoal = goalRepository.save(goal);
        logger.info("Goal created with ID: {}", savedGoal.getId());
        return convertToDTO(savedGoal);
    }

    @Transactional(readOnly = true)
    public List<GoalDTO> getAllGoals(String username) {
        logger.debug("Fetching all goals for user: {}", username);
        return goalRepository.findByUserUsername(username)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public GoalDTO updateGoal(Long goalId, GoalDTO goalDTO, String username) {
        logger.info("Updating goal ID: {} for user: {}", goalId, username);
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found"));
        if (!goal.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Unauthorized access to goal");
        }
        goal.setName(goalDTO.getName());
        goal.setTargetAmount(goalDTO.getTargetAmount());
        goal.setSavedAmount(goalDTO.getSavedAmount());
        goal.setDeadline(goalDTO.getDeadline());
        Goal updatedGoal = goalRepository.save(goal);
        logger.info("Goal updated successfully: {}", goalId);
        return convertToDTO(updatedGoal);
    }

    @Transactional
    public void deleteGoal(Long goalId, String username) {
        logger.info("Deleting goal ID: {} for user: {}", goalId, username);
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found"));
        if (!goal.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Unauthorized access to goal");
        }
        goalRepository.deleteById(goalId);
        logger.info("Goal deleted successfully: {}", goalId);
    }

    private GoalDTO convertToDTO(Goal goal) {
        return new GoalDTO(
                goal.getName(),
                goal.getTargetAmount(),
                goal.getSavedAmount(),
                goal.getDeadline(),
                goal.getUser().getUsername()
        );
    }
}