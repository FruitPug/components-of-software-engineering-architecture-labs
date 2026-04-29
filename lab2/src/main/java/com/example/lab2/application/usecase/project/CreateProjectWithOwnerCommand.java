package com.example.lab2.application.usecase.project;

public record CreateProjectWithOwnerCommand (
        String name,
        String description,
        Long ownerId
) { }
