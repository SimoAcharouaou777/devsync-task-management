// src/main/java/com/youcode/devsync/service/DashboardService.java
package com.youcode.devsync.service;

import com.youcode.devsync.repository.DashboardRepository;

public class DashboardService {

    private final DashboardRepository repository;

    public DashboardService() {
        this.repository = new DashboardRepository();
    }

    public int getTotalUsers() {
        return repository.getTotalUsers();
    }

    public int getTotalManagers() {
        return repository.getTotalManagers();
    }

    public int getTotalTasks() {
        return repository.getTotalTasks();
    }

    public int getCompletedTasks() {
        return repository.getCompletedTasks();
    }

    public int getNonCompletedTasks() {
        return repository.getNonCompletedTasks();
    }

}