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

    public List<Tag> findAll(){
        EntityManager em = emf.createEntityManager();
        List<Tag> tags = em.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
        em.close();
        return tags;
    }

  public Tag findById(long id){
        EntityManager em = emf.createEntityManager();
        Tag tag = em.find(Tag.class, id);
        em.close();
        return tag;
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