package com.example.expensetrack.service;

import com.example.expensetrack.dto.CategoryTypeDTO;
import com.example.expensetrack.model.CategoryType;
import com.example.expensetrack.repository.CategoryTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryTypeService {

    @Autowired
    private CategoryTypeRepository categoryTypeRepository;

    public List<CategoryTypeDTO> getAllCategoryTypes() {
        return categoryTypeRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CategoryTypeDTO getCategoryTypeById(Integer categoryTypeId) {
        return categoryTypeRepository.findById(categoryTypeId).map(this::convertToDTO).orElse(null);
    }

    public CategoryTypeDTO createCategoryType(CategoryTypeDTO categoryTypeDTO) {
        CategoryType categoryType = convertToEntity(categoryTypeDTO);
        CategoryType savedCategoryType = categoryTypeRepository.save(categoryType);
        return convertToDTO(savedCategoryType);
    }

    public CategoryTypeDTO updateCategoryType(Integer categoryTypeId, CategoryTypeDTO categoryTypeDTO) {
        CategoryType categoryType = convertToEntity(categoryTypeDTO);
        categoryType.setCategoryTypeId(categoryTypeId);
        CategoryType updatedCategoryType = categoryTypeRepository.save(categoryType);
        return convertToDTO(updatedCategoryType);
    }

    public void deleteCategoryType(Integer categoryTypeId) {
        categoryTypeRepository.deleteById(categoryTypeId);
    }

    private CategoryTypeDTO convertToDTO(CategoryType categoryType) {
        return new CategoryTypeDTO(categoryType.getCategoryTypeId(), categoryType.getCategoryName());
    }

    private CategoryType convertToEntity(CategoryTypeDTO categoryTypeDTO) {
        return new CategoryType(categoryTypeDTO.getCategoryTypeId(), categoryTypeDTO.getCategoryName());
    }
}
