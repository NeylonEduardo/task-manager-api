package com.ney.taskmanager.infrastructure.HTTP.Request;

import com.ney.taskmanager.application.input.UpdateTaskInput;
import com.ney.taskmanager.domain.TaskStatus;

import java.util.Optional;

public record UpdateTaskRequest(
        Optional<String> title,
        Optional<String> description,
        Optional<String> status) {

    public UpdateTaskInput toInput() {
        return new UpdateTaskInput(title, description, status.map(TaskStatus::valueOf));
    }
}
