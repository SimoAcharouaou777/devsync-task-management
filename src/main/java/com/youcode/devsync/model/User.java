package com.youcode.devsync.model;

import com.youcode.devsync.model.enums.UserRole;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @Column(name = "tickets")
    private Integer tickets;

    @Column(name = "last_assigned_task_deleted_at")
    private Timestamp lastAssignedTaskDeletedAt;

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChangeRequest> changeRequestMade;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChangeRequest> changeRequestManaged;

    @PrePersist
    @PreUpdate
    private void setDefaultTickets(){
        if(this.userRole == UserRole.MANAGER){
            this.tickets = null;
        } else if (this.tickets == null) {
            this.tickets = 2;
        }
    }

    public User() {
    }

    public User(String username, String password, String firstName, String lastName, String email, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public int getTickets() {
        return tickets;
    }
    public void setTickets(int tickets) {
        this.tickets = tickets;
    }
    public Timestamp getLastAssignedTaskDeletedAt() {
        return lastAssignedTaskDeletedAt;
    }
    public void setLastAssignedTaskDeletedAt(Timestamp lastAssignedTaskDeletedAt) {
        this.lastAssignedTaskDeletedAt = lastAssignedTaskDeletedAt;
    }
}