package com.ney.taskmanager.infrastructure.HTTP.Request;

import com.ney.taskmanager.application.input.CreateTaskInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public record CreateTaskRequest(
        @NotBlank
        @Size(min = 3, max = 100)
        String title,

        Optional<@Size(max = 500) String> description) {

    public CreateTaskInput toInput() {
        return new CreateTaskInput(title, description);
    }
}