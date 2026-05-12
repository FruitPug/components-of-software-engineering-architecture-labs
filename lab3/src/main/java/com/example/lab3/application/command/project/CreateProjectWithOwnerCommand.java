package com.example.lab3.application.command.project;

public record CreateProjectWithOwnerCommand (
        String name,
        String description,
        Long ownerId
) { }
