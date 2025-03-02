package com.expensetracker.service;

import com.expensetracker.dto.AuthRequest;
import com.expensetracker.dto.ForgotPasswordDTO;
import com.expensetracker.dto.UserDTO;
import com.expensetracker.model.User;
import com.expensetracker.repository.UserRepository;
import com.expensetracker.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        logger.info("Attempting to register user: {}", userDTO.getUsername());
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            logger.warn("Username already exists: {}", userDTO.getUsername());
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User(userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()));

        logger.info("Saving new user: {}", userDTO.getUsername());
        User savedUser = userRepository.save(user);
        logger.info("User saved with ID: {}", savedUser.getId());

        return new UserDTO(savedUser.getUsername());
    }

    public String authenticateUser(AuthRequest request) {
        logger.info("Authenticating user: {}", request.getUsername());
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    logger.warn("Authentication failed: User not found - {}", request.getUsername());
                    return new RuntimeException("Invalid username or password");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.warn("Authentication failed: Invalid password for user - {}", request.getUsername());
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        logger.info("User authenticated successfully: {}", request.getUsername());
        return token;
    }

    @Transactional
    public String resetPassword(ForgotPasswordDTO forgotPasswordDTO) {
        logger.info("Attempting password reset for user: {}", forgotPasswordDTO.getUsername());
        if (!forgotPasswordDTO.getNewPassword().equals(forgotPasswordDTO.getConfirmPassword())) {
            logger.warn("Password reset failed: Passwords do not match for user - {}", forgotPasswordDTO.getUsername());
            throw new IllegalArgumentException("Passwords do not match");
        }

        User user = userRepository.findByUsername(forgotPasswordDTO.getUsername())
                .orElseThrow(() -> {
                    logger.warn("Password reset failed: User not found - {}", forgotPasswordDTO.getUsername());
                    return new RuntimeException("User not found");
                });

        user.setPassword(passwordEncoder.encode(forgotPasswordDTO.getNewPassword()));
        userRepository.save(user);
        logger.info("Password updated successfully for user: {}", forgotPasswordDTO.getUsername());
        return "Password updated successfully";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Loading user details for: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.warn("User not found: {}", username);
                    return new UsernameNotFoundException("User not found: " + username);
                });

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}