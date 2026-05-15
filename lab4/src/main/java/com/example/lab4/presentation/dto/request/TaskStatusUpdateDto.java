package com.example.lab4.presentation.dto.request;

import com.example.lab4.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskStatusUpdateDto {

    @NotNull
    private Long taskId;

    @NotNull
    private TaskStatus status;
}
