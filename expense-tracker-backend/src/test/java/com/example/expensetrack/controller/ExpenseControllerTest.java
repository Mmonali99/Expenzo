package com.example.expensetrack.controller;

import com.example.expensetrack.controller.ExpenseController;
import com.example.expensetrack.dto.ExpenseDTO;
import com.example.expensetrack.service.ExpenseService;
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
import java.time.LocalTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExpenseController.class)
@AutoConfigureMockMvc
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

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
    public void testGetAllExpenses() throws Exception {
        Mockito.when(expenseService.getAllExpenses()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/expenses")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetExpenseById() throws Exception {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        Mockito.when(expenseService.getExpenseById(1L)).thenReturn(expenseDTO);

        mockMvc.perform(get("/api/expenses/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateExpense() throws Exception {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setDate(LocalDate.now());
        expenseDTO.setTime(LocalTime.now());
        expenseDTO.setExpenseAmount(50.0);
        Mockito.when(expenseService.createExpense(Mockito.any(ExpenseDTO.class))).thenReturn(expenseDTO);

        mockMvc.perform(post("/api/expenses")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateExpense() throws Exception {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setDate(LocalDate.now());
        expenseDTO.setTime(LocalTime.now());
        expenseDTO.setExpenseAmount(100.0);
        Mockito.when(expenseService.updateExpense(Mockito.eq(1L), Mockito.any(ExpenseDTO.class))).thenReturn(expenseDTO);

        mockMvc.perform(put("/api/expenses/1")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteExpense() throws Exception {
        mockMvc.perform(delete("/api/expenses/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
    }
}
