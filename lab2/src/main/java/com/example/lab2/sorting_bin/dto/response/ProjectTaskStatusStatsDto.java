package com.example.lab2.sorting_bin.dto.response;

import com.example.lab2.sorting_bin.entity.enums.TaskStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectTaskStatusStatsDto {
    Long projectId;
    TaskStatus status;
    Long taskCount;
}
