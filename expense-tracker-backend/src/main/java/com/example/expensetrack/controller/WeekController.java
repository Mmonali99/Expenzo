package com.example.expensetrack.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensetrack.utility.WeekDetails;

@RestController
@RequestMapping("/api")
public class WeekController {

    @GetMapping("/weeks")
    public List<WeekDetails.WeekInfo> getWeeks(@RequestParam int year, @RequestParam int month) {
        WeekDetails weekDetails = new WeekDetails();
        return weekDetails.getWeekDetails(year, month);
    }
}

