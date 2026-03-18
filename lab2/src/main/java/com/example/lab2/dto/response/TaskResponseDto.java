package com.example.lab2.dto.response;

import com.example.lab2.entity.enums.TaskPriority;
import com.example.lab2.entity.enums.TaskStatus;
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
