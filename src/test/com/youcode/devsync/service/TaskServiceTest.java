package com.youcode.devsync.service;

import com.youcode.devsync.model.Task;
import com.youcode.devsync.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;
    @InjectMocks
    TaskService taskService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void verifyTaskWrongDeadLine() {
        Task task = new Task();
        task.setTitle("task title");
        task.setDescription("task description");
        task.setDeadline(Timestamp.valueOf("2024-10-14 00:00:00"));
        task.setStatus("PENDING");
        Timestamp threeDaysFromNow = Timestamp.valueOf(LocalDateTime.now().plusDays(3));
        assertFalse(task.getDeadline().after(threeDaysFromNow), "the task deade line should be 3 days from now");

    }

    @Test
    void verifyTaskCorrectDeadLine(){
        Task task = new Task();
        task.setTitle("task title");
        task.setDescription("task description");
        task.setDeadline(Timestamp.valueOf("2027-10-04 00:00:00"));
        task.setStatus("PENDING");
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        assertTrue(task.getDeadline().after(now), "the task is inserted successfully");
    }

    @Test
    void getTasksById1() {

    }

    @Test
    void getAllTasks() {
    }

    @Test
    void getTasksByCreatorId() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void getOverdueTasks() {
    }
}