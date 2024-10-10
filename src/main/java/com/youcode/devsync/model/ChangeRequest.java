package com.youcode.devsync.model;


import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "change_requests")
public class ChangeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    @Column(name = "request_date", nullable = false)
    private Timestamp requestDate;


    // Getters and Setters

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }
    public User getRequester() {
        return requester;
    }
    public void setRequester(User requester) {
        this.requester = requester;
    }
    public User getManager() {
        return manager;
    }
    public void setManager(User manager) {
        this.manager = manager;
    }
    public Timestamp getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }
}
