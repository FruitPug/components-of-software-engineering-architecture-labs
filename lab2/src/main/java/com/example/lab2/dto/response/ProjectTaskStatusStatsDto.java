package com.example.lab2.dto.response;

import com.example.lab2.entity.enums.TaskStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectTaskStatusStatsDto {
    Long projectId;
    TaskStatus status;
    Long taskCount;
}
