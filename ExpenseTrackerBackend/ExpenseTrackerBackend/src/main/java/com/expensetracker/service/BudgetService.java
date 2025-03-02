package com.expensetracker.service;

import com.expensetracker.dto.BudgetCategoryDTO;
import com.expensetracker.dto.BudgetDTO;
import com.expensetracker.dto.CategoryDTO;
import com.expensetracker.model.Budget;
import com.expensetracker.model.BudgetCategory;
import com.expensetracker.model.Category;
import com.expensetracker.model.User;
import com.expensetracker.repository.BudgetCategoryRepository;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    private static final Logger logger = LoggerFactory.getLogger(BudgetService.class);

    private final BudgetRepository budgetRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    public BudgetService(BudgetRepository budgetRepository, BudgetCategoryRepository budgetCategoryRepository,
                         CategoryRepository categoryRepository, UserRepository userRepository,
                         ExpenseRepository expenseRepository) {
        this.budgetRepository = budgetRepository;
        this.budgetCategoryRepository = budgetCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    public BudgetDTO createBudget(BudgetDTO budgetDTO, String username) {
        logger.info("Creating budget for user: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Budget budget = new Budget();
        budget.setTotalBudgetAmount(budgetDTO.getTotalBudgetAmount());
        budget.setMonth(budgetDTO.getMonth());
        budget.setYear(budgetDTO.getYear());
        budget.setUser(user);
        Budget savedBudget = budgetRepository.save(budget);
        logger.info("Budget created with ID: {}", savedBudget.getId());
        return convertToDTO(savedBudget);
    }

    public BudgetDTO getBudgetById(Long id, String username) {
        logger.debug("Fetching budget ID: {} for user: {}", id, username);
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found"));
        if (!budget.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Unauthorized access to budget");
        }
        return convertToDTO(budget);
    }

    public BudgetDTO getBudgetWithSpent(Long id, String username) {
        logger.debug("Fetching budget with spent amount ID: {} for user: {}", id, username);
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found"));
        if (!budget.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Unauthorized access to budget");
        }

        List<String> categories = budget.getBudgetCategories().stream()
                .map(bc -> bc.getCategory().getName())
                .collect(Collectors.toList());
        LocalDate start = LocalDate.of(budget.getYear(), budget.getMonth(), 1);
        LocalDate end = start.plusMonths(1).minusDays(1);
        Double spent = expenseRepository.calculateSpentAmount(username, start, end, categories);
        if (spent == null) spent = 0.0;

        BudgetDTO dto = convertToDTO(budget);
        dto.setSpent(spent);
        return dto;
    }

    public BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO, String username) {
        logger.info("Updating budget ID: {} for user: {}", id, username);
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found"));
        if (!budget.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Unauthorized access to budget");
        }
        budget.setTotalBudgetAmount(budgetDTO.getTotalBudgetAmount());
        budget.setMonth(budgetDTO.getMonth());
        budget.setYear(budgetDTO.getYear());
        Budget updatedBudget = budgetRepository.save(budget);
        logger.info("Budget updated successfully: {}", id);
        return convertToDTO(updatedBudget);
    }

    public void deleteBudget(Long id, String username) {
        logger.info("Deleting budget ID: {} for user: {}", id, username);
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found"));
        if (!budget.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Unauthorized access to budget");
        }
        budgetRepository.deleteById(id);
        logger.info("Budget deleted successfully: {}", id);
    }

    public List<BudgetDTO> getAllBudgets(String username) {
        logger.debug("Fetching all budgets for user: {}", username);
        return budgetRepository.findAll().stream()
                .filter(budget -> budget.getUser().getUsername().equals(username))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BudgetDTO addCategoryToBudget(Long budgetId, String categoryName, Double allocatedAmount, String username) {
        logger.info("Adding category {} to budget ID: {} for user: {}", categoryName, budgetId, username);
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found"));
        if (!budget.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Unauthorized access to budget");
        }
        Category category = categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(categoryName);
                    return categoryRepository.save(newCategory);
                });
        BudgetCategory budgetCategory = new BudgetCategory();
        budgetCategory.setBudget(budget);
        budgetCategory.setCategory(category);
        budgetCategory.setAllocatedAmount(allocatedAmount != null ? allocatedAmount : 0.0);
        budget.getBudgetCategories().add(budgetCategory);
        budgetCategoryRepository.save(budgetCategory);
        Budget updatedBudget = budgetRepository.save(budget);
        logger.info("Category added to budget: {}", budgetId);
        return convertToDTO(updatedBudget);
    }

    public BudgetDTO calculateBudgetValues(Long budgetId, String username) {
        logger.debug("Calculating values for budget ID: {} for user: {}", budgetId, username);
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found"));
        if (!budget.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Unauthorized access to budget");
        }
        if (budget.getTotalBudgetAmount() == null) {
            budget.setTotalBudgetAmount(0.0);
        }
        Budget updatedBudget = budgetRepository.save(budget);
        return convertToDTO(updatedBudget);
    }

    private BudgetDTO convertToDTO(Budget budget) {
        List<BudgetCategoryDTO> budgetCategories = budget.getBudgetCategories().stream()
                .map(bc -> new BudgetCategoryDTO(
                        bc.getId(),
                        new CategoryDTO(bc.getCategory().getId(), bc.getCategory().getName()),
                        bc.getAllocatedAmount()))
                .collect(Collectors.toList());
        return new BudgetDTO(
                budget.getId(),
                budget.getTotalBudgetAmount(),
                budget.getMonth(),
                budget.getYear(),
                budgetCategories,
                null // Spent is set in getBudgetWithSpent
        );
    }
}