package com.example.lab1.service;

import com.example.lab1.dto.response.ProjectTaskStatusStatsDto;
import com.example.lab1.dto.response.UserDoneTasksStatsDto;
import com.example.lab1.repository.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    public ResponseEntity<Page<ProjectTaskStatusStatsDto>> getTasksByProjectAndStatus(Pageable pageable) {
        return ResponseEntity.ok(analyticsRepository.tasksByProjectAndStatus(pageable));
    }

    public ResponseEntity<Page<UserDoneTasksStatsDto>> getTopAssigneesByDoneTasks(Pageable pageable) {
        return ResponseEntity.ok(analyticsRepository.topAssigneesByDoneTasks(pageable));
    }
}
