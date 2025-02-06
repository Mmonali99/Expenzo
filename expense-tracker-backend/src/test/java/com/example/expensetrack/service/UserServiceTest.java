package com.example.expensetrack.service;

import com.example.expensetrack.dto.UserDTO;
import com.example.expensetrack.model.User;
import com.example.expensetrack.repository.UserRepository;
import com.example.expensetrack.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setup() {
        user = new User(1L, "testuser", passwordEncoder.encode("password"), "1234567890");
        userDTO = new UserDTO(1L, "testuser", "password", null, "1234567890");
    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        assertFalse(userService.getAllUsers().isEmpty());
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDTO found = userService.getUserById(1L);
        assertEquals(user.getUserId(), found.getUserId());
    }

    @Test
    public void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO created = userService.createUser(userDTO);
        assertEquals(user.getUserId(), created.getUserId());
    }

    @Test
    public void testLogin() {
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        UserDTO loginDTO = new UserDTO("testuser", "password", (String) null);
        UserDTO loggedInUser = userService.login(loginDTO);
        assertNotNull(loggedInUser);
        assertEquals(user.getUsername(), loggedInUser.getUsername());
    }

    @Test
    public void testLoginFailed() {
        when(userRepository.findByUsername("testuser")).thenReturn(null);
        UserDTO loginDTO = new UserDTO("testuser", "password", (String) null);
        UserDTO loggedInUser = userService.login(loginDTO);
        assertNull(loggedInUser);
    }

    @Test
    public void testResetPassword() {
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        UserDTO resetPasswordDTO = new UserDTO("testuser", "password", "newpassword");
        boolean success = userService.resetPassword(resetPasswordDTO);
        assertTrue(success);
    }

    @Test
    public void testResetPasswordFailed() {
        when(userRepository.findByUsername("testuser")).thenReturn(null);
        UserDTO resetPasswordDTO = new UserDTO("testuser", "password", "newpassword");
        boolean success = userService.resetPassword(resetPasswordDTO);
        assertFalse(success);
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO updated = userService.updateUser(1L, userDTO);
        assertEquals(user.getUserId(), updated.getUserId());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetUserByUsername() {
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        UserDTO found = userService.getUserByUsername("testuser");
        assertEquals(user.getUsername(), found.getUsername());
    }

    @Test
    public void testGetUserByUsernameNotFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(null);
        UserDTO found = userService.getUserByUsername("testuser");
        assertNull(found);
    }
}
