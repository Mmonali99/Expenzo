package com.example.expensetrack.controller;

import com.example.expensetrack.controller.BudgetController;
import com.example.expensetrack.dto.BudgetDTO;
import com.example.expensetrack.service.BudgetService;
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
@WebMvcTest(BudgetController.class)
@AutoConfigureMockMvc
public class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BudgetService budgetService;

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
    public void testGetAllBudgets() throws Exception {
        Mockito.when(budgetService.getAllBudgets()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/budgets")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBudgetById() throws Exception {
        BudgetDTO budgetDTO = new BudgetDTO(1L, 1000.0, null, Collections.emptyList());
        Mockito.when(budgetService.getBudgetById(1L)).thenReturn(budgetDTO);

        mockMvc.perform(get("/api/budgets/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateBudget() throws Exception {
        BudgetDTO budgetDTO = new BudgetDTO(null, 1000.0, null, Collections.emptyList());
        Mockito.when(budgetService.createBudget(Mockito.any(BudgetDTO.class))).thenReturn(budgetDTO);

        mockMvc.perform(post("/api/budgets")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(budgetDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateBudget() throws Exception {
        BudgetDTO budgetDTO = new BudgetDTO(1L, 2000.0, null, Collections.emptyList());
        Mockito.when(budgetService.updateBudget(Mockito.eq(1L), Mockito.any(BudgetDTO.class))).thenReturn(budgetDTO);

        mockMvc.perform(put("/api/budgets/1")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(budgetDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteBudget() throws Exception {
        mockMvc.perform(delete("/api/budgets/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
    }
}
