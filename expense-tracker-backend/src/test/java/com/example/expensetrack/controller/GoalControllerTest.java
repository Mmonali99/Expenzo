package com.example.expensetrack.controller;

import com.example.expensetrack.controller.GoalController;
import com.example.expensetrack.dto.GoalDTO;
import com.example.expensetrack.service.GoalService;
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

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GoalController.class)
@AutoConfigureMockMvc
public class GoalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoalService goalService;

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
    public void testGetAllGoals() throws Exception {
        Mockito.when(goalService.getAllGoals()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/goals")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetGoalById() throws Exception {
        GoalDTO goalDTO = new GoalDTO();
        Mockito.when(goalService.getGoalById(1L)).thenReturn(goalDTO);

        mockMvc.perform(get("/api/goals/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateGoal() throws Exception {
        GoalDTO goalDTO = new GoalDTO();
        goalDTO.setTitle("Save Money");
        goalDTO.setDescription("Save $1000");
        goalDTO.setGoalType("Financial");
        goalDTO.setGoalAmount(1000.0);
        goalDTO.setTargetDate(LocalDate.now().plusMonths(6));
        goalDTO.setProgress(0);
        Mockito.when(goalService.createGoal(Mockito.any(GoalDTO.class))).thenReturn(goalDTO);

        mockMvc.perform(post("/api/goals")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(goalDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateGoal() throws Exception {
        GoalDTO goalDTO = new GoalDTO();
        goalDTO.setTitle("Save More Money");
        goalDTO.setDescription("Save $2000");
        goalDTO.setGoalType("Financial");
        goalDTO.setGoalAmount(2000.0);
        goalDTO.setTargetDate(LocalDate.now().plusMonths(12));
        goalDTO.setProgress(10);
        Mockito.when(goalService.updateGoal(Mockito.eq(1L), Mockito.any(GoalDTO.class))).thenReturn(goalDTO);

        mockMvc.perform(put("/api/goals/1")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(goalDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteGoal() throws Exception {
        mockMvc.perform(delete("/api/goals/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
    }
}
