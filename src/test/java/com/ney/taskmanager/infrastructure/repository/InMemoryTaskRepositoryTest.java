package com.ney.taskmanager.infrastructure.repository;

import com.ney.taskmanager.infrastructure.TaskRepository;
import com.ney.taskmanager.infrastructure.TaskRepositoryTest;

class InMemoryTaskRepositoryTest extends TaskRepositoryTest {
    @Override
    protected TaskRepository createRepository() {
        return new InMemoryTaskRepository();
    }
}