package com.example.expensetrack.controller;

import com.example.expensetrack.controller.WeeklyController;
import com.example.expensetrack.dto.WeeklyDTO;
import com.example.expensetrack.service.WeeklyService;
import com.example.expensetrack.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WeeklyController.class)
@AutoConfigureMockMvc
public class WeeklyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeeklyService weeklyService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;

    @BeforeEach
    public void setup() {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("testuser")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        jwtToken = jwtUtil.generateToken(userDetails);
    }

    @Test
    public void testGetAllWeeklyDetails() throws Exception {
        Mockito.when(weeklyService.getAllWeeklyDetails()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/weekly")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetWeeklyDetailById() throws Exception {
        WeeklyDTO weeklyDTO = new WeeklyDTO();
        Mockito.when(weeklyService.getWeeklyDetailById(1L)).thenReturn(weeklyDTO);

        mockMvc.perform(get("/api/weekly/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateWeeklyDetail() throws Exception {
        WeeklyDTO weeklyDTO = new WeeklyDTO();
        weeklyDTO.setWeekNumber(1);
        weeklyDTO.setWeeklyPercentage(5.0);
        weeklyDTO.setWeeklyAmount(50.0);
        Mockito.when(weeklyService.createWeeklyDetail(Mockito.any(WeeklyDTO.class))).thenReturn(weeklyDTO);

        mockMvc.perform(post("/api/weekly")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(weeklyDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateWeeklyDetail() throws Exception {
        WeeklyDTO weeklyDTO = new WeeklyDTO();
        weeklyDTO.setWeekNumber(2);
        weeklyDTO.setWeeklyPercentage(10.0);
        weeklyDTO.setWeeklyAmount(100.0);
        Mockito.when(weeklyService.updateWeeklyDetail(Mockito.eq(1L), Mockito.any(WeeklyDTO.class))).thenReturn(weeklyDTO);

        mockMvc.perform(put("/api/weekly/1")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(weeklyDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteWeeklyDetail() throws Exception {
        mockMvc.perform(delete("/api/weekly/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
    }
}
