package com.youcode.devsync.repository;

import com.youcode.devsync.model.User;
import com.youcode.devsync.model.enums.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");

    public void save(User user){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public void update(User user){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(User user){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        user = em.merge(user);
        em.remove(user);
        em.getTransaction().commit();
        em.close();
    }

    public User findByUsername(String username){
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        User user = query.getSingleResult();
        em.close();
        return user;
    }

    public List<User> findByRole(UserRole role){
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userRole = :role", User.class);
        query.setParameter("role", role);
        List<User> users = query.getResultList();
        em.close();
        return users;
    }

    public void close(){
        if(emf != null){
            emf.close();
        }
    }
}