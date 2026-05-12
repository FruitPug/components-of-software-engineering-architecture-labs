package com.example.lab3.presentation.dto.response;

import com.example.lab3.domain.enums.TaskPriority;
import com.example.lab3.domain.enums.TaskStatus;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class TaskResponseDto {
    Long id;
    String title;
    String description;

    TaskStatus status;
    TaskPriority priority;

    LocalDate dueDate;

    Long projectId;
    Long creatorUserId;
    Long assigneeUserId;

}
