package com.example.expensetrack.service;

import com.example.expensetrack.dto.MonthlyDTO;
import com.example.expensetrack.model.Monthly;
import com.example.expensetrack.repository.MonthlyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonthlyService {

    @Autowired
    private MonthlyRepository monthlyRepository;

    public List<MonthlyDTO> getAllMonthlyDetails() {
        return monthlyRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public MonthlyDTO getMonthlyDetailById(Long monthlyId) {
        return monthlyRepository.findById(monthlyId).map(this::convertToDTO).orElse(null);
    }

    public MonthlyDTO createMonthlyDetail(MonthlyDTO monthlyDTO) {
        Monthly monthly = convertToEntity(monthlyDTO);
        Monthly savedMonthly = monthlyRepository.save(monthly);
        return convertToDTO(savedMonthly);
    }

    public MonthlyDTO updateMonthlyDetail(Long monthlyId, MonthlyDTO monthlyDTO) {
        Monthly monthly = convertToEntity(monthlyDTO);
        monthly.setMonthlyId(monthlyId);
        Monthly updatedMonthly = monthlyRepository.save(monthly);
        return convertToDTO(updatedMonthly);
    }

    public void deleteMonthlyDetail(Long monthlyId) {
        monthlyRepository.deleteById(monthlyId);
    }

    public List<MonthlyDTO> getMonthlyByCategoryId(Integer categoryId) {
        return monthlyRepository.findByCategoryCategoryId(categoryId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private MonthlyDTO convertToDTO(Monthly monthly) {
        MonthlyDTO monthlyDTO = new MonthlyDTO();
        monthlyDTO.setMonthlyId(monthly.getMonthlyId());
        monthlyDTO.setMonthlyPercentage(monthly.getMonthlyPercentage());
        monthlyDTO.setMonthlyAmount(monthly.getMonthlyAmount());
        monthlyDTO.setSpentPercentage(monthly.getSpentPercentage());
        monthlyDTO.setSpentAmount(monthly.getSpentAmount());
        monthlyDTO.setRemainingPercentage(monthly.getRemainingPercentage());
        monthlyDTO.setRemainingAmount(monthly.getRemainingAmount());
        return monthlyDTO;
    }

    private Monthly convertToEntity(MonthlyDTO monthlyDTO) {
        Monthly monthly = new Monthly();
        monthly.setMonthlyId(monthlyDTO.getMonthlyId());
        monthly.setMonthlyPercentage(monthlyDTO.getMonthlyPercentage());
        monthly.setMonthlyAmount(monthlyDTO.getMonthlyAmount());
        monthly.setSpentPercentage(monthlyDTO.getSpentPercentage());
        monthly.setSpentAmount(monthlyDTO.getSpentAmount());
        monthly.setRemainingPercentage(monthlyDTO.getRemainingPercentage());
        monthly.setRemainingAmount(monthlyDTO.getRemainingAmount());
        return monthly;
    }
}
