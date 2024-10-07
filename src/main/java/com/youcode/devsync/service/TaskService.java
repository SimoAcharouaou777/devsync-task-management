package com.youcode.devsync.service;

import com.youcode.devsync.model.Task;
import com.youcode.devsync.repository.TaskRepository;

public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(){
        taskRepository = new TaskRepository();
    }

    public void addTask(Task task){
        taskRepository.save(task);
    }

}
