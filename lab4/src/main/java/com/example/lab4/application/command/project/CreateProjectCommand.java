package com.example.lab4.application.command.project;

public record CreateProjectCommand(
        String name,
        String description
) {
}
