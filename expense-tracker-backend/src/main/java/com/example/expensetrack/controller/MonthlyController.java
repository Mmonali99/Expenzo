package com.example.expensetrack.controller;

import com.example.expensetrack.dto.MonthlyDTO;
import com.example.expensetrack.service.MonthlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monthly")
public class MonthlyController {

    @Autowired
    private MonthlyService monthlyService;

    @GetMapping
    public List<MonthlyDTO> getAllMonthlyDetails() {
        return monthlyService.getAllMonthlyDetails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonthlyDTO> getMonthlyDetailById(@PathVariable Long id) {
        MonthlyDTO monthly = monthlyService.getMonthlyDetailById(id);
        return monthly != null ? ResponseEntity.ok(monthly) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<MonthlyDTO> createMonthlyDetail(@RequestBody MonthlyDTO monthlyDTO) {
        MonthlyDTO createdMonthly = monthlyService.createMonthlyDetail(monthlyDTO);
        return ResponseEntity.ok(createdMonthly);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonthlyDTO> updateMonthlyDetail(@PathVariable Long id, @RequestBody MonthlyDTO monthlyDTO) {
        MonthlyDTO updatedMonthly = monthlyService.updateMonthlyDetail(id, monthlyDTO);
        return ResponseEntity.ok(updatedMonthly);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonthlyDetail(@PathVariable Long id) {
        monthlyService.deleteMonthlyDetail(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public List<MonthlyDTO> getMonthlyByCategoryId(@PathVariable Integer categoryId) {
        return monthlyService.getMonthlyByCategoryId(categoryId);
    }
}
