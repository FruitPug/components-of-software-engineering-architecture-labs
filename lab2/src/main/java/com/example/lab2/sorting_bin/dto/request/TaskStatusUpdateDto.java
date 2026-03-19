package com.example.lab2.sorting_bin.dto.request;

import com.example.lab2.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskStatusUpdateDto {

    @NotNull
    private Long taskId;

    @NotNull
    private TaskStatus status;
}
