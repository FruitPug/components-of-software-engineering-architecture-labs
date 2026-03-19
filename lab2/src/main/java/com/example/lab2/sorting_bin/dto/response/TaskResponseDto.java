package com.example.lab2.sorting_bin.dto.response;

import com.example.lab2.domain.enums.TaskPriority;
import com.example.lab2.domain.enums.TaskStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TaskResponseDto {
    String title;
    String description;

    TaskStatus status;
    TaskPriority priority;

    Long projectId;
    Long creatorUserId;
    Long assigneeUserId;

}
