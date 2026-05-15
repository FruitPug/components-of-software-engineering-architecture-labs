package com.example.lab4.presentation.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TaskTagResponseDto {
    Long taskId;
    Long tagId;
}
