package com.ney.taskmanager.application;

import com.ney.taskmanager.application.input.UpdateTaskInput;
import com.ney.taskmanager.application.output.TaskOutput;
import com.ney.taskmanager.domain.TaskId;
import com.ney.taskmanager.domain.TaskNotFoundException;
import com.ney.taskmanager.infrastructure.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateTaskUseCase {
    private final TaskRepository repository;

    public UpdateTaskUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public TaskOutput execute(TaskId taskId, UpdateTaskInput input) {
        var task = repository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        task.update(input.title(), input.description(), input.status());
        var updated = repository.save(task);
        return TaskOutput.from(task);
    }
}
