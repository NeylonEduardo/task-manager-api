package com.ney.taskmanager.application;

import com.ney.taskmanager.application.output.TaskOutput;
import com.ney.taskmanager.domain.TaskStatus;
import com.ney.taskmanager.infrastructure.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetTaskByStatus {
    private final TaskRepository repository;

    public GetTaskByStatus(TaskRepository repository) {
        this.repository = repository;
    }

    public List<TaskOutput> execute(TaskStatus status) {
        return repository.findByStatus(status)
                .stream()
                .map(TaskOutput::from)
                .toList();
    }
}