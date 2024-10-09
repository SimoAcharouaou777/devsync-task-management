package com.youcode.devsync.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_to", referencedColumnName = "id")
    @OnDelete(action =  OnDeleteAction.SET_NULL)
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @OnDelete(action =  OnDeleteAction.SET_NULL)
    private User createdBy;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "deadline")
    private Timestamp deadline;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

    @Column(name = "canBeReassigned", nullable = false)
    private boolean canBeReassigned = true;

    @Column(name = "overdue", nullable = false)
    private boolean overdue = false;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "task_tags",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChangeRequest> changeRequests;

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public boolean isCanBeReassigned() {
        return canBeReassigned;
    }
    public void setCanBeReassigned(boolean canBeReassigned) {
        this.canBeReassigned = canBeReassigned;
    }
    public boolean isOverdue() {
        return overdue;
    }
    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }
}