package com.ney.taskmanager.infrastructure;

import com.ney.taskmanager.domain.Task;
import com.ney.taskmanager.domain.TaskId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    List<Task> findAll();
    Optional<Task> findById(TaskId id);
    void delete(TaskId id);
}
