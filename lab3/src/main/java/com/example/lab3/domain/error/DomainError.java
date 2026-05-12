package com.example.lab3.domain.error;

public class DomainError extends RuntimeException {
    public DomainError(String message) {
        super(message);
    }
}