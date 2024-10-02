package com.youcode.devsync.repository;

import com.youcode.devsync.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class UserRepository {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");

    public void save(User user){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
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

    public void close(){
        if(emf != null){
            emf.close();
        }
    }
}
