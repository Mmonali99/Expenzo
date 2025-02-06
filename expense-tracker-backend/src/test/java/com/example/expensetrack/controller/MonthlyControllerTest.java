package com.example.expensetrack.controller;

import com.example.expensetrack.controller.MonthlyController;
import com.example.expensetrack.dto.MonthlyDTO;
import com.example.expensetrack.service.MonthlyService;
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
@WebMvcTest(MonthlyController.class)
@AutoConfigureMockMvc
public class MonthlyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonthlyService monthlyService;

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
    public void testGetAllMonthlyDetails() throws Exception {
        Mockito.when(monthlyService.getAllMonthlyDetails()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/monthly")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMonthlyDetailById() throws Exception {
        MonthlyDTO monthlyDTO = new MonthlyDTO();
        Mockito.when(monthlyService.getMonthlyDetailById(1L)).thenReturn(monthlyDTO);

        mockMvc.perform(get("/api/monthly/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateMonthlyDetail() throws Exception {
        MonthlyDTO monthlyDTO = new MonthlyDTO();
        Mockito.when(monthlyService.createMonthlyDetail(Mockito.any(MonthlyDTO.class))).thenReturn(monthlyDTO);

        mockMvc.perform(post("/api/monthly")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(monthlyDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateMonthlyDetail() throws Exception {
        MonthlyDTO monthlyDTO = new MonthlyDTO();
        Mockito.when(monthlyService.updateMonthlyDetail(Mockito.eq(1L), Mockito.any(MonthlyDTO.class))).thenReturn(monthlyDTO);

        mockMvc.perform(put("/api/monthly/1")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(monthlyDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteMonthlyDetail() throws Exception {
        mockMvc.perform(delete("/api/monthly/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
    }
}
