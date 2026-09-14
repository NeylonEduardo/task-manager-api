package com.ney.taskmanager.infrastructure.HTTP.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ney.taskmanager.application.output.TaskOutput;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
public record TaskResponse(String id, String title, String description, String status) {
    public static TaskResponse from(TaskOutput output) {
        return new TaskResponse(output.id(),
                output.title(),
                output.description().orElse(null),
                output.status());
    }
}
