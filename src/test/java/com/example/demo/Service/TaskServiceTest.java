package com.example.demo.Service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.example.demo.Model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        // A new TaskService is created for each test.
        // Its in-memory collection therefore starts empty.
    }

    @Test
    void getAllTasksReturnsEmptyListInitially() {
        assertTrue(taskService.getAllTasks().isEmpty());
    }

    @Test
    void createTaskAssignsIdAndStoresTask() {
        Task task = taskService.createTask("Learn CI/CD");

        assertEquals(1L, task.id());
        assertEquals("Learn CI/CD", task.title());
        assertEquals(List.of(task), taskService.getAllTasks());
    }

    @Test
    void createTaskTrimsTitle() {
        Task task = taskService.createTask("  Learn Maven  ");

        assertEquals("Learn Maven", task.title());
    }

    @Test
    void getTaskByIdReturnsTaskWhenPresent() {
        Task created = taskService.createTask("Write tests");

        assertTrue(taskService.getTaskById(created.id()).isPresent());
        assertEquals(
                created,
                taskService.getTaskById(created.id()).orElseThrow()
        );
    }

    @Test
    void getTaskByIdReturnsEmptyWhenMissing() {
        assertTrue(taskService.getTaskById(999L).isEmpty());
    }

    @Test
    void deleteTaskRemovesExistingTask() {
        Task created = taskService.createTask("Delete me");

        assertTrue(taskService.deleteTask(created.id()));
        assertTrue(taskService.getTaskById(created.id()).isEmpty());
        assertTrue(taskService.getAllTasks().isEmpty());
    }

    @Test
    void deleteTaskReturnsFalseWhenMissing() {
        assertFalse(taskService.deleteTask(999L));
    }
}
