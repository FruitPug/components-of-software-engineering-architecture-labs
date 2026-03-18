package com.example.lab2.sorting_bin.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TaskCommentResponseDto {
    Long taskId;
    Long authorId;
    String body;
}
