package com.ney.taskmanager.application;

import com.ney.taskmanager.domain.TaskId;
import com.ney.taskmanager.domain.TaskNotFoundException;
import com.ney.taskmanager.infrastructure.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteTaskUseCase {
    private final TaskRepository repository;

    public DeleteTaskUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public void execute(TaskId taskId) {
        if (repository.findById(taskId).isEmpty()) {
            throw new TaskNotFoundException(taskId);
        }

        repository.delete(taskId);
    }
}
