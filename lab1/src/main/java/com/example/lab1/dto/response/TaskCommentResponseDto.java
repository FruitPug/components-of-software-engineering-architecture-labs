package com.example.lab1.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TaskCommentResponseDto {
    Long taskId;
    Long authorId;
    String body;
}
