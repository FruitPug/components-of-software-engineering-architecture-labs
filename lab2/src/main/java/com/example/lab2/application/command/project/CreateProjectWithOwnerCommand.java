package com.example.lab2.application.command.project;

public record CreateProjectWithOwnerCommand (
        String name,
        String description,
        Long ownerId
) { }
