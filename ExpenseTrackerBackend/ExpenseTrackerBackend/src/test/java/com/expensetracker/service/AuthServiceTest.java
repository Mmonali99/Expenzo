package com.expensetracker.service;

import com.expensetracker.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    public void testRegisterUser() {
        String uniqueUsername = "testuser" + System.currentTimeMillis(); // Ensure uniqueness
        UserDTO userDTO = new UserDTO(uniqueUsername, "password"); // Use current constructor
        UserDTO result = authService.registerUser(userDTO);
        assertEquals(uniqueUsername, result.getUsername());
        // Removed email assertion since email is no longer in UserDTO
    }
}