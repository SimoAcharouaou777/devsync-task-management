package com.youcode.devsync.repository;

import com.youcode.devsync.model.Task;
import jakarta.persistence.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private EntityManagerFactory emf;

    public TaskRepository(){
        emf = Persistence.createEntityManagerFactory("myJPAUnit");
    }

    public void save(Task task){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(task);
        em.getTransaction().commit();
        em.close();
    }

    public List<Task> getTasksById(long userId){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Task> query = em.createQuery("SELECT t FROM Task t WHERE t.assignedTo.id = :userId OR t.createdBy.id = :userId", Task.class);
        query.setParameter("userId", userId);
        List<Task> tasks = query.getResultList();
        em.close();
        return tasks;
    }

    public List<Task> getTasksByCreatorId(long userId){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Task> query = em.createQuery("SELECT t FROM Task t WHERE t.createdBy.id = :userId", Task.class);
        query.setParameter("userId", userId);
        List<Task> tasks = query.getResultList();
        em.close();
        return tasks;
    }

    public void delete(long taskId){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Task task = em.find(Task.class, taskId);
        if(task != null){
            em.remove(task);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void update(Task task) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(task);
        em.getTransaction().commit();
        em.close();
    }

    public Task findById(long id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Task> query = em.createQuery(
                "SELECT t FROM Task t LEFT JOIN FETCH t.assignedTo WHERE t.id = :id", Task.class);
        query.setParameter("id", id);
        Task task;
        try {
            task = query.getSingleResult();
        } catch (NoResultException e) {
            task = null;
        }
        em.close();
        return task;
    }
}
