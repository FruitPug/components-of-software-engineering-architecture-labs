package com.example.lab4.domain.error;

public class DomainError extends RuntimeException {
    public DomainError(String message) {
        super(message);
    }
}