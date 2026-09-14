package com.ney.taskmanager.application;

import com.ney.taskmanager.application.input.CreateTaskInput;
import com.ney.taskmanager.application.output.TaskOutput;
import com.ney.taskmanager.domain.Task;
import com.ney.taskmanager.infrastructure.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTaskUseCaseTest {
    @Mock
    TaskRepository repository;

    @InjectMocks
    CreateTaskUseCase useCase;

    @Test
    void should_create_task_successfully() {
        // given
        var input = new CreateTaskInput("Estudar Java", Optional.of("Finalizar o modulo records"));

        when(repository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        TaskOutput output = useCase.execute(input);

        // then
        assertNotNull(output);
        assertNotNull(output.id());
        assertEquals("Estudar Java", output.title());
        assertEquals(Optional.of("Finalizar o modulo records"), output.description());

        verify(repository, times(1)).save(any(Task.class));
    }
}