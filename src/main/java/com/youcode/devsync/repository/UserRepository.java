package com.youcode.devsync.repository;

import com.youcode.devsync.model.User;
import com.youcode.devsync.model.enums.UserRole;
import jakarta.persistence.*;

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
        try{
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }finally {
            em.close();
        }
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