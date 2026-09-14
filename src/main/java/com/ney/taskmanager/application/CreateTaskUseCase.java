package com.ney.taskmanager.application;

import com.ney.taskmanager.application.input.CreateTaskInput;
import com.ney.taskmanager.application.output.TaskOutput;
import com.ney.taskmanager.domain.Task;
import com.ney.taskmanager.infrastructure.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateTaskUseCase {
    private final TaskRepository repository;

    public CreateTaskUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public TaskOutput execute(CreateTaskInput input) {
        var task = new Task(input.title(), input.description());
        var saved = repository.save(task);
        return TaskOutput.from(saved);
    }
}
