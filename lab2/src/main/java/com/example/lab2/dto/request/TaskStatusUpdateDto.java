package com.example.lab2.dto.request;

import com.example.lab2.entity.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskStatusUpdateDto {

    @NotNull
    private Long taskId;

    @NotNull
    private TaskStatus status;
}
