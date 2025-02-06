package com.example.expensetrack.controller;

import com.example.expensetrack.controller.CategoryController;
import com.example.expensetrack.dto.CategoryDTO;
import com.example.expensetrack.service.CategoryService;
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
@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

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
    public void testGetAllCategories() throws Exception {
        Mockito.when(categoryService.getAllCategories()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/categories")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCategoryById() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        Mockito.when(categoryService.getCategoryById(1)).thenReturn(categoryDTO);

        mockMvc.perform(get("/api/categories/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        Mockito.when(categoryService.createCategory(Mockito.any(CategoryDTO.class))).thenReturn(categoryDTO);

        mockMvc.perform(post("/api/categories")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        Mockito.when(categoryService.updateCategory(Mockito.eq(1), Mockito.any(CategoryDTO.class))).thenReturn(categoryDTO);

        mockMvc.perform(put("/api/categories/1")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCategory() throws Exception {
        mockMvc.perform(delete("/api/categories/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
    }
}
