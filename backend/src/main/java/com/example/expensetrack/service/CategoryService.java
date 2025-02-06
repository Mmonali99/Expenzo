package com.example.expensetrack.service;

import com.example.expensetrack.dto.CategoryDTO;
import com.example.expensetrack.dto.CategoryTypeDTO;
import com.example.expensetrack.dto.BudgetDTO;
import com.example.expensetrack.model.Category;
import com.example.expensetrack.model.CategoryType;
import com.example.expensetrack.model.Budget;
import com.example.expensetrack.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId).map(this::convertToDTO).orElse(null);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    public CategoryDTO updateCategory(Integer categoryId, CategoryDTO categoryDTO) {
        Category category = convertToEntity(categoryDTO);
        category.setCategoryId(categoryId);
        Category updatedCategory = categoryRepository.save(category);
        return convertToDTO(updatedCategory);
    }

    public void deleteCategory(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());

        // Handling nested CategoryType object
        if (category.getCategoryType() != null) {
            CategoryType categoryType = category.getCategoryType();
            categoryDTO.setCategoryType(new CategoryTypeDTO(categoryType.getCategoryTypeId(), categoryType.getCategoryName()));
        }

        // Handling nested Budget object
        if (category.getBudget() != null) {
            Budget budget = category.getBudget();
            categoryDTO.setBudget(new BudgetDTO(budget.getBudgetId(), budget.getBudgetAmount(), null, null));
        }

        return categoryDTO;
    }

    private Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());

        // Handling nested CategoryTypeDTO object
        if (categoryDTO.getCategoryType() != null) {
            CategoryTypeDTO categoryTypeDTO = categoryDTO.getCategoryType();
            category.setCategoryType(new CategoryType(categoryTypeDTO.getCategoryTypeId(), categoryTypeDTO.getCategoryName()));
        }

        // Handling nested BudgetDTO object
        if (categoryDTO.getBudget() != null) {
            BudgetDTO budgetDTO = categoryDTO.getBudget();
            Budget budget = new Budget();
            budget.setBudgetId(budgetDTO.getBudgetId());
            budget.setBudgetAmount(budgetDTO.getBudgetAmount());
            category.setBudget(budget);
        }

        return category;
    }
}
