package com.example.expensetrack.exception;

public class CategoryTypeNotFoundException extends RuntimeException {
    public CategoryTypeNotFoundException(String message) {
        super(message);
    }
}
