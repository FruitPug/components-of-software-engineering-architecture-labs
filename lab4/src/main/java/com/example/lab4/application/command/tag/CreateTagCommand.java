package com.example.lab4.application.command.tag;

public record CreateTagCommand(
        String name,
        String color
) {
}
