package com.example.expensetrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.expensetrack.repository")
@EntityScan(basePackages = "com.example.expensetrack.model")
public class ExpenseTrackManagementAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackManagementAppApplication.class, args);
    }
}
