package com.example.lab2.domain.service;

public interface PasswordHasher {
    String hash(String rawPassword);
}
