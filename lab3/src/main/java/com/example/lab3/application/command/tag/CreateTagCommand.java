package com.example.lab3.application.command.tag;

public record CreateTagCommand(
        String name,
        String color
) {
}
