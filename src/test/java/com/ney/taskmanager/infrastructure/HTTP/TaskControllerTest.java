package com.ney.taskmanager.infrastructure.HTTP;

import com.ney.taskmanager.application.CreateTaskUseCase;
import com.ney.taskmanager.application.DeleteTaskUseCase;
import com.ney.taskmanager.application.GetTaskByIdUseCase;
import com.ney.taskmanager.application.GetTasksUseCase;
import com.ney.taskmanager.application.UpdateTaskUseCase;
import com.ney.taskmanager.application.output.TaskOutput;
import com.ney.taskmanager.domain.TaskId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({
        MockitoExtension.class,
        RestDocumentationExtension.class
})
class TaskControllerTest {

    private static final UUID TASK_ID =
            UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

    private MockMvc mockMvc;

    @Mock
    private CreateTaskUseCase createTaskUseCase;

    @Mock
    private GetTasksUseCase getTasksUseCase;

    @Mock
    private GetTaskByIdUseCase getTaskByIdUseCase;

    @Mock
    private DeleteTaskUseCase deleteTaskUseCase;

    @Mock
    private UpdateTaskUseCase updateTaskUseCase;

    @BeforeEach
    void setUp(RestDocumentationContextProvider documentation) {
        TaskController controller = new TaskController(
                createTaskUseCase,
                getTasksUseCase,
                getTaskByIdUseCase,
                deleteTaskUseCase,
                updateTaskUseCase
        );

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .apply(documentationConfiguration(documentation))
                .alwaysDo(document(
                        "{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .build();
    }

    @Test
    void createTask() throws Exception {
        TaskOutput output = new TaskOutput(
                TASK_ID.toString(),
                "Study Java",
                Optional.of("Study Spring REST Docs"),
                "PENDING"
        );

        when(createTaskUseCase.execute(any()))
                .thenReturn(output);

        mockMvc.perform(
                        post("/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "title": "Study Java",
                                          "description": "Study Spring REST Docs"
                                        }
                                        """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(TASK_ID.toString()))
                .andExpect(jsonPath("$.title").value("Study Java"))
                .andExpect(jsonPath("$.description")
                        .value("Study Spring REST Docs"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andDo(document(
                        "create-task",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title")
                                        .description("Task title"),
                                fieldWithPath("description")
                                        .description("Optional task description")
                                        .optional()
                        ),
                        responseFields(
                                fieldWithPath("id")
                                        .description("Generated task identifier"),
                                fieldWithPath("title")
                                        .description("Task title"),
                                fieldWithPath("description")
                                        .description("Task description")
                                        .optional(),
                                fieldWithPath("status")
                                        .description("Current task status")
                        )
                ));
    }

    @Test
    void listTasks() throws Exception {
        TaskOutput firstTask = new TaskOutput(
                TASK_ID.toString(),
                "Study Java",
                Optional.of("Study REST Docs"),
                "PENDING"
        );

        TaskOutput secondTask = new TaskOutput(
                UUID.fromString(
                        "123e4567-e89b-12d3-a456-426614174000"
                ).toString(),
                "Create project",
                Optional.empty(),
                "IN_PROGRESS"
        );

        when(getTasksUseCase.execute())
                .thenReturn(List.of(firstTask, secondTask));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(document(
                        "list-tasks",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].id")
                                        .description("Task identifier"),
                                fieldWithPath("[].title")
                                        .description("Task title"),
                                fieldWithPath("[].description")
                                        .description("Task description")
                                        .optional(),
                                fieldWithPath("[].status")
                                        .description("Current task status")
                        )
                ));
    }

    @Test
    void getTaskById() throws Exception {
        TaskOutput output = new TaskOutput(
                TASK_ID.toString(),
                "Study Java",
                Optional.of("Study REST Docs"),
                "PENDING"
        );

        when(getTaskByIdUseCase.execute(any(TaskId.class)))
                .thenReturn(output);

        mockMvc.perform(
                        get("/tasks/{id}", TASK_ID)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TASK_ID.toString()))
                .andDo(document(
                        "get-task-by-id",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id")
                                        .description("Task UUID")
                        ),
                        responseFields(
                                fieldWithPath("id")
                                        .description("Task identifier"),
                                fieldWithPath("title")
                                        .description("Task title"),
                                fieldWithPath("description")
                                        .description("Task description")
                                        .optional(),
                                fieldWithPath("status")
                                        .description("Current task status")
                        )
                ));
    }

    @Test
    void updateTask() throws Exception {
        TaskOutput output = new TaskOutput(
                TASK_ID.toString(),
                "Study Spring Boot",
                Optional.of("Practice REST documentation"),
                "IN_PROGRESS"
        );

        when(updateTaskUseCase.execute(
                any(TaskId.class),
                any()
        )).thenReturn(output);

        mockMvc.perform(
                        patch("/tasks/{id}", TASK_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "title": "Study Spring Boot",
                                          "description": "Practice REST documentation",
                                          "status": "IN_PROGRESS"
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title")
                        .value("Study Spring Boot"))
                .andExpect(jsonPath("$.status")
                        .value("IN_PROGRESS"))
                .andDo(document(
                        "update-task",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id")
                                        .description("UUID of the task being updated")
                        ),
                        requestFields(
                                fieldWithPath("title")
                                        .description("New task title")
                                        .optional(),
                                fieldWithPath("description")
                                        .description("New task description")
                                        .optional(),
                                fieldWithPath("status")
                                        .description(
                                                "New status: PENDING, IN_PROGRESS or COMPLETED"
                                        )
                                        .optional()
                        ),
                        responseFields(
                                fieldWithPath("id")
                                        .description("Task identifier"),
                                fieldWithPath("title")
                                        .description("Updated task title"),
                                fieldWithPath("description")
                                        .description("Updated task description")
                                        .optional(),
                                fieldWithPath("status")
                                        .description("Updated task status")
                        )
                ));
    }

    @Test
    void deleteTask() throws Exception {
        doNothing()
                .when(deleteTaskUseCase)
                .execute(any(TaskId.class));

        mockMvc.perform(
                        delete("/tasks/{id}", TASK_ID)
                )
                .andExpect(status().isNoContent())
                .andDo(document(
                        "delete-task",
                        pathParameters(
                                parameterWithName("id")
                                        .description("UUID of the task being deleted")
                        )
                ));
    }
}