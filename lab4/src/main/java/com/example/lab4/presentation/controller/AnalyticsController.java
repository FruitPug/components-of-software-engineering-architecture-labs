package com.example.lab4.presentation.controller;

import com.example.lab4.infrastructure.persistence.repository.JpaAnalyticsRepository;
import com.example.lab4.presentation.dto.response.ProjectTaskStatusStatsDto;
import com.example.lab4.presentation.dto.response.UserDoneTasksStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final JpaAnalyticsRepository jpaAnalyticsRepository;

    @GetMapping("/projects/tasks-by-status")
    public ResponseEntity<Page<ProjectTaskStatusStatsDto>> tasksByProjectAndStatus(Pageable pageable) {
        return ResponseEntity.ok(jpaAnalyticsRepository.tasksByProjectAndStatus(pageable));
    }

    @GetMapping("/users/top-done")
    public ResponseEntity<Page<UserDoneTasksStatsDto>> topUsersByDoneTasks(
            Pageable pageable
    ) {
        return ResponseEntity.ok(jpaAnalyticsRepository.topAssigneesByDoneTasks(pageable));
    }
}
