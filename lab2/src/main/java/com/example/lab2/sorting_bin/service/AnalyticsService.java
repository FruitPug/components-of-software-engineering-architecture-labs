package com.example.lab2.sorting_bin.service;

import com.example.lab2.sorting_bin.dto.response.ProjectTaskStatusStatsDto;
import com.example.lab2.sorting_bin.dto.response.UserDoneTasksStatsDto;
import com.example.lab2.sorting_bin.repository.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    public Page<ProjectTaskStatusStatsDto> getTasksByProjectAndStatus(Pageable pageable) {
        return analyticsRepository.tasksByProjectAndStatus(pageable);
    }

    public Page<UserDoneTasksStatsDto> getTopAssigneesByDoneTasks(Pageable pageable) {
        return analyticsRepository.topAssigneesByDoneTasks(pageable);
    }
}
