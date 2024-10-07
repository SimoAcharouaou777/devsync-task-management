package com.youcode.devsync.service;

import com.youcode.devsync.model.Task;
import com.youcode.devsync.repository.TaskRepository;

import java.util.List;

public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(){
        taskRepository = new TaskRepository();
    }

    public void addTask(Task task){
        taskRepository.save(task);
    }

    public List<Task> getTasksById(long userId){
        List<Task> tasks = taskRepository.getTasksById(userId);
        for(Task task : tasks){
            task.getTags().size();
        }
        return tasks;
    }

    public void deleteTask(long taskId){
        taskRepository.delete(taskId);
    }

    public void updateTask(Task task){
        taskRepository.update(task);
    }

    public Task findById(long id) {
        return taskRepository.findById(id);
    }
}
