package com.example.expensetrack.controller;

import com.example.expensetrack.dto.CategoryTypeDTO;
import com.example.expensetrack.service.CategoryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-types")
public class CategoryTypeController {

    @Autowired
    private CategoryTypeService categoryTypeService;

    @GetMapping
    public List<CategoryTypeDTO> getAllCategoryTypes() {
        return categoryTypeService.getAllCategoryTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryTypeDTO> getCategoryTypeById(@PathVariable Integer id) {
        CategoryTypeDTO categoryType = categoryTypeService.getCategoryTypeById(id);
        return categoryType != null ? ResponseEntity.ok(categoryType) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CategoryTypeDTO> createCategoryType(@RequestBody CategoryTypeDTO categoryTypeDTO) {
        CategoryTypeDTO createdCategoryType = categoryTypeService.createCategoryType(categoryTypeDTO);
        return ResponseEntity.ok(createdCategoryType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryTypeDTO> updateCategoryType(@PathVariable Integer id, @RequestBody CategoryTypeDTO categoryTypeDTO) {
        CategoryTypeDTO updatedCategoryType = categoryTypeService.updateCategoryType(id, categoryTypeDTO);
        return ResponseEntity.ok(updatedCategoryType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryType(@PathVariable Integer id) {
        categoryTypeService.deleteCategoryType(id);
        return ResponseEntity.noContent().build();
    }
}
