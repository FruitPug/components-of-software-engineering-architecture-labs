package com.example.lab4.application.command.project;

public record CreateProjectWithOwnerCommand (
        String name,
        String description,
        Long ownerId
) { }
