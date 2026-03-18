package com.example.lab2.domain.error;

public class DomainError extends RuntimeException {
    public DomainError(String message) {
        super(message);
    }
}