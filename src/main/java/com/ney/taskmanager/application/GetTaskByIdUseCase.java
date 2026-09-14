package com.ney.taskmanager.application;

import com.ney.taskmanager.application.output.TaskOutput;
import com.ney.taskmanager.domain.TaskId;
import com.ney.taskmanager.domain.TaskNotFoundException;
import com.ney.taskmanager.infrastructure.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class GetTaskByIdUseCase {
    private final TaskRepository repository;

    public GetTaskByIdUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public TaskOutput execute(TaskId id) {
        return repository.findById(id)
                .map(TaskOutput::from)
                .orElseThrow(()
                        -> new TaskNotFoundException(id));
    }
}
