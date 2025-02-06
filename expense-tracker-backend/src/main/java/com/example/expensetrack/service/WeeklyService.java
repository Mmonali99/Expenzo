package com.example.expensetrack.service;

import com.example.expensetrack.dto.WeeklyDTO;
import com.example.expensetrack.model.Weekly;
import com.example.expensetrack.repository.WeeklyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeeklyService {

    @Autowired
    private WeeklyRepository weeklyRepository;

    public List<WeeklyDTO> getAllWeeklyDetails() {
        return weeklyRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public WeeklyDTO getWeeklyDetailById(Long weeklyId) {
        return weeklyRepository.findById(weeklyId).map(this::convertToDTO).orElse(null);
    }

    public WeeklyDTO createWeeklyDetail(WeeklyDTO weeklyDTO) {
        Weekly weekly = convertToEntity(weeklyDTO);
        Weekly savedWeekly = weeklyRepository.save(weekly);
        return convertToDTO(savedWeekly);
    }

    public WeeklyDTO updateWeeklyDetail(Long weeklyId, WeeklyDTO weeklyDTO) {
        Weekly weekly = convertToEntity(weeklyDTO);
        weekly.setWeeklyId(weeklyId);
        Weekly updatedWeekly = weeklyRepository.save(weekly);
        return convertToDTO(updatedWeekly);
    }

    public void deleteWeeklyDetail(Long weeklyId) {
        weeklyRepository.deleteById(weeklyId);
    }

    public List<WeeklyDTO> getWeeklyByCategoryId(Integer categoryId) {
        return weeklyRepository.findByCategoryCategoryId(categoryId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private WeeklyDTO convertToDTO(Weekly weekly) {
        WeeklyDTO weeklyDTO = new WeeklyDTO();
        weeklyDTO.setWeeklyId(weekly.getWeeklyId());
        weeklyDTO.setWeeklyPercentage(weekly.getWeeklyPercentage());
        weeklyDTO.setWeeklyAmount(weekly.getWeeklyAmount());
        weeklyDTO.setSpentPercentage(weekly.getSpentPercentage());
        weeklyDTO.setSpentAmount(weekly.getSpentAmount());
        weeklyDTO.setRemainingPercentage(weekly.getRemainingPercentage());
        weeklyDTO.setRemainingAmount(weekly.getRemainingAmount());
        return weeklyDTO;
    }

    private Weekly convertToEntity(WeeklyDTO weeklyDTO) {
        Weekly weekly = new Weekly();
        weekly.setWeeklyId(weeklyDTO.getWeeklyId());
        weekly.setWeeklyPercentage(weeklyDTO.getWeeklyPercentage());
        weekly.setWeeklyAmount(weeklyDTO.getWeeklyAmount());
        weekly.setSpentPercentage(weeklyDTO.getSpentPercentage());
        weekly.setSpentAmount(weeklyDTO.getSpentAmount());
        weekly.setRemainingPercentage(weeklyDTO.getRemainingPercentage());
        weekly.setRemainingAmount(weeklyDTO.getRemainingAmount());
        return weekly;
    }
}
