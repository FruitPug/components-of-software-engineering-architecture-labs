package com.example.lab3.domain.service;

public interface PasswordHasher {
    String hash(String rawPassword);
}
