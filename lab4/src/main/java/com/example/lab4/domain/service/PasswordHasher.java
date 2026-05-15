package com.example.lab4.domain.service;

public interface PasswordHasher {
    String hash(String rawPassword);
}
