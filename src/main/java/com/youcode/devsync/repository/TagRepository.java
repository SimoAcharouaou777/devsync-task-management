package com.youcode.devsync.repository;

import com.youcode.devsync.model.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class TagRepository {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");
    private EntityManager em = emf.createEntityManager();

    public void save(Tag tag) {
        em.getTransaction().begin();
        em.persist(tag);
        em.getTransaction().commit();
    }

    public List<Tag> findAll() {
        return em.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
    }

    public boolean isEmpty() {
        long count = em.createQuery("SELECT COUNT(t) FROM Tag t", Long.class).getSingleResult();
        return count == 0;
    }

    public void close() {
        em.close();
        emf.close();
    }
}