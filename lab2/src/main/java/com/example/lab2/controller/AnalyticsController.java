package com.example.lab2.controller;

import com.example.lab2.dto.response.ProjectTaskStatusStatsDto;
import com.example.lab2.dto.response.UserDoneTasksStatsDto;
import com.example.lab2.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/projects/tasks-by-status")
    public ResponseEntity<Page<ProjectTaskStatusStatsDto>> tasksByProjectAndStatus(Pageable pageable) {
        return ResponseEntity.ok(analyticsService.getTasksByProjectAndStatus(pageable));
    }

    @GetMapping("/users/top-done")
    public ResponseEntity<Page<UserDoneTasksStatsDto>> topUsersByDoneTasks(
            Pageable pageable
    ) {
        return ResponseEntity.ok(analyticsService.getTopAssigneesByDoneTasks(pageable));
    }
}
