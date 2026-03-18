package com.example.lab2.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDoneTasksStatsDto {
    Long userId;
    String email;
    Long doneCount;
    Integer rank;
}
