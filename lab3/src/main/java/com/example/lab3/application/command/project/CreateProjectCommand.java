package com.example.lab3.application.command.project;

public record CreateProjectCommand(
        String name,
        String description
) {
}
