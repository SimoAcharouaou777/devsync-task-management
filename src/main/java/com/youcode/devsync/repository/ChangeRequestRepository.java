package com.youcode.devsync.repository;

import com.youcode.devsync.model.ChangeRequest;
import com.youcode.devsync.model.Task;
import com.youcode.devsync.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Optional;

public class ChangeRequestRepository {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");
    private EntityManager em = emf.createEntityManager();

    public Optional<ChangeRequest> findByTaskAndRequester(Task task, User requester){
        EntityManager em = emf.createEntityManager();
        String query = "SELECT cr FROM ChangeRequest cr WHERE cr.task = :task AND cr.requester = :requester";
        try {
            ChangeRequest changeRequest = em.createQuery(query, ChangeRequest.class)
                    .setParameter("task", task)
                    .setParameter("requester", requester)
                    .getSingleResult();
            return Optional.of(changeRequest);
        } catch (Exception e){
            return Optional.empty();
        }finally {
            em.close();
        }
    }

    public void save(ChangeRequest changeRequest){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(changeRequest);
            em.getTransaction().commit();
        }catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw e;
        }finally {
            em.close();
        }
    }
}
