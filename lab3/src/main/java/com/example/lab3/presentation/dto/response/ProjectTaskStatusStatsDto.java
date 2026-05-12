package com.example.lab3.presentation.dto.response;

import com.example.lab3.domain.enums.TaskStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectTaskStatusStatsDto {
    Long projectId;
    TaskStatus status;
    Long taskCount;
}
