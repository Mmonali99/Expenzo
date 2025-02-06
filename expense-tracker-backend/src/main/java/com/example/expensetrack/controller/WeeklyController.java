package com.example.expensetrack.controller;

import com.example.expensetrack.dto.WeeklyDTO;
import com.example.expensetrack.service.WeeklyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekly")
public class WeeklyController {

    @Autowired
    private WeeklyService weeklyService;

    @GetMapping
    public List<WeeklyDTO> getAllWeeklyDetails() {
        return weeklyService.getAllWeeklyDetails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeeklyDTO> getWeeklyDetailById(@PathVariable Long id) {
        WeeklyDTO weekly = weeklyService.getWeeklyDetailById(id);
        return weekly != null ? ResponseEntity.ok(weekly) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<WeeklyDTO> createWeeklyDetail(@RequestBody WeeklyDTO weeklyDTO) {
        WeeklyDTO createdWeekly = weeklyService.createWeeklyDetail(weeklyDTO);
        return ResponseEntity.ok(createdWeekly);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeeklyDTO> updateWeeklyDetail(@PathVariable Long id, @RequestBody WeeklyDTO weeklyDTO) {
        WeeklyDTO updatedWeekly = weeklyService.updateWeeklyDetail(id, weeklyDTO);
        return ResponseEntity.ok(updatedWeekly);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeeklyDetail(@PathVariable Long id) {
        weeklyService.deleteWeeklyDetail(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public List<WeeklyDTO> getWeeklyByCategoryId(@PathVariable Integer categoryId) {
        return weeklyService.getWeeklyByCategoryId(categoryId);
    }
}
