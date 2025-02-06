package com.example.expensetrack.service;

import com.example.expensetrack.dto.CategoryTypeDTO;
import com.example.expensetrack.model.CategoryType;
import com.example.expensetrack.repository.CategoryTypeRepository;
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
public class CategoryTypeServiceTest {

    @Autowired
    private CategoryTypeService categoryTypeService;

    @MockBean
    private CategoryTypeRepository categoryTypeRepository;

    private CategoryType categoryType;
    private CategoryTypeDTO categoryTypeDTO;

    @BeforeEach
    public void setup() {
        categoryType = new CategoryType(1, "Food");
        categoryTypeDTO = new CategoryTypeDTO(1, "Food");
    }

    @Test
    public void testGetAllCategoryTypes() {
        when(categoryTypeRepository.findAll()).thenReturn(Collections.singletonList(categoryType));
        assertFalse(categoryTypeService.getAllCategoryTypes().isEmpty());
    }

    @Test
    public void testGetCategoryTypeById() {
        when(categoryTypeRepository.findById(1)).thenReturn(Optional.of(categoryType));
        CategoryTypeDTO found = categoryTypeService.getCategoryTypeById(1);
        assertEquals(categoryType.getCategoryTypeId(), found.getCategoryTypeId());
    }

    @Test
    public void testCreateCategoryType() {
        when(categoryTypeRepository.save(any(CategoryType.class))).thenReturn(categoryType);
        CategoryTypeDTO created = categoryTypeService.createCategoryType(categoryTypeDTO);
        assertEquals(categoryType.getCategoryTypeId(), created.getCategoryTypeId());
    }

    @Test
    public void testUpdateCategoryType() {
        when(categoryTypeRepository.save(any(CategoryType.class))).thenReturn(categoryType);
        CategoryTypeDTO updated = categoryTypeService.updateCategoryType(1, categoryTypeDTO);
        assertEquals(categoryType.getCategoryTypeId(), updated.getCategoryTypeId());
    }

    @Test
    public void testDeleteCategoryType() {
        doNothing().when(categoryTypeRepository).deleteById(1);
        categoryTypeService.deleteCategoryType(1);
        verify(categoryTypeRepository, times(1)).deleteById(1);
    }
}
