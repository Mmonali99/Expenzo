package com.example.expensetrack.service;

import com.example.expensetrack.dto.*;
import com.example.expensetrack.model.Category;
import com.example.expensetrack.model.CategoryType;
import com.example.expensetrack.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    public void setup() {
        CategoryType categoryType = new CategoryType(1, "Food");
        category = new Category(1, "Groceries", categoryType, null);
        CategoryTypeDTO categoryTypeDTO = new CategoryTypeDTO(1, "Food");
        UserDTO userDTO = new UserDTO(); // Assuming you have a UserDTO class
        categoryDTO = new CategoryDTO(1, "Groceries", categoryTypeDTO, null, null, null, null, userDTO);
    }

    @Test
    public void testGetAllCategories() {
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));
        assertFalse(categoryService.getAllCategories().isEmpty());
    }

    @Test
    public void testGetCategoryById() {
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        CategoryDTO found = categoryService.getCategoryById(1);
        assertEquals(category.getCategoryId(), found.getCategoryId());
    }

    @Test
    public void testCreateCategory() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        CategoryDTO created = categoryService.createCategory(categoryDTO);
        assertEquals(category.getCategoryId(), created.getCategoryId());
    }

    @Test
    public void testUpdateCategory() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        CategoryDTO updated = categoryService.updateCategory(1, categoryDTO);
        assertEquals(category.getCategoryId(), updated.getCategoryId());
    }

    @Test
    public void testDeleteCategory() {
        doNothing().when(categoryRepository).deleteById(1);
        categoryService.deleteCategory(1);
        verify(categoryRepository, times(1)).deleteById(1);
    }
}
