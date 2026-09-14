package com.ney.taskmanager.infrastructure.repository;

import com.ney.taskmanager.domain.Task;
import com.ney.taskmanager.domain.TaskId;
import com.ney.taskmanager.domain.TaskStatus;
import com.ney.taskmanager.infrastructure.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryTaskRepository implements TaskRepository {
    private final Map<TaskId, Task> storage = new HashMap<>();

    @Override
    public Task save(Task task) {
        storage.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Task> findById(TaskId id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void delete(TaskId id) {
        storage.remove(id);
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {
        return storage.values()
                .stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }
}
