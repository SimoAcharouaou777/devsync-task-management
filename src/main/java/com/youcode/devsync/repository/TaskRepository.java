package com.youcode.devsync.repository;

import com.youcode.devsync.model.Task;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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
}
