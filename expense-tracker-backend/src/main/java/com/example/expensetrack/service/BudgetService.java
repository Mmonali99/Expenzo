package com.example.expensetrack.service;

import com.example.expensetrack.dto.BudgetDTO;
import com.example.expensetrack.dto.CategoryDTO;
import com.example.expensetrack.dto.UserDTO;
import com.example.expensetrack.exception.BudgetNotFoundException;
import com.example.expensetrack.model.Budget;
import com.example.expensetrack.model.Category;
import com.example.expensetrack.model.User;
import com.example.expensetrack.repository.BudgetRepository;
import com.example.expensetrack.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<BudgetDTO> getAllBudgets() {
        return budgetRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public BudgetDTO getBudgetById(Long budgetId) {
        return budgetRepository.findById(budgetId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found with id: " + budgetId));
    }


    public BudgetDTO createBudget(BudgetDTO budgetDTO) {
        Budget budget = convertToEntity(budgetDTO);
        Budget savedBudget = budgetRepository.save(budget);
        return convertToDTO(savedBudget);
    }

    public BudgetDTO updateBudget(Long budgetId, BudgetDTO budgetDTO) {
        Budget budget = convertToEntity(budgetDTO);
        budget.setBudgetId(budgetId);
        Budget updatedBudget = budgetRepository.save(budget);
        return convertToDTO(updatedBudget);
    }

    public void deleteBudget(Long budgetId) {
        budgetRepository.deleteById(budgetId);
    }

    private BudgetDTO convertToDTO(Budget budget) {
        BudgetDTO budgetDTO = new BudgetDTO();
        budgetDTO.setBudgetId(budget.getBudgetId());
        budgetDTO.setBudgetAmount(budget.getBudgetAmount());
        budgetDTO.setUser(new UserDTO(budget.getUser().getUserId(), budget.getUser().getUsername(), budget.getUser().getPhoneNumber()));
        List<CategoryDTO> categories = budget.getCategories().stream().map(this::convertCategoryToDTO).collect(Collectors.toList());
        budgetDTO.setCategories(categories);
        return budgetDTO;
    }

    private Budget convertToEntity(BudgetDTO budgetDTO) {
        Budget budget = new Budget();
        budget.setBudgetId(budgetDTO.getBudgetId());
        budget.setBudgetAmount(budgetDTO.getBudgetAmount());
        budget.setUser(new User(budgetDTO.getUser().getUserId(), budgetDTO.getUser().getUsername(), budgetDTO.getUser().getPassword(), budgetDTO.getUser().getPhoneNumber()));
        Set<Category> categories = budgetDTO.getCategories().stream().map(this::convertCategoryToEntity).collect(Collectors.toSet());
        budget.setCategories(categories);
        return budget;
    }

    private CategoryDTO convertCategoryToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        return categoryDTO;
    }

    private Category convertCategoryToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        return category;
    }
}
