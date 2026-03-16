package com.example.lab1.dto.request;

import com.example.lab1.entity.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskStatusUpdateDto {

    @NotBlank
    private Long taskId;

    @NotBlank
    private TaskStatus status;
}
