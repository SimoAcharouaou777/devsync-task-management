package com.youcode.devsync.repository;

import com.youcode.devsync.model.ChangeRequest;
import com.youcode.devsync.model.Task;
import com.youcode.devsync.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class ChangeRequestRepository {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");

    public Optional<ChangeRequest> findByTaskAndRequester(Task task, User requester) {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT cr FROM ChangeRequest cr WHERE cr.task = :task AND cr.requester = :requester";
        try {
            ChangeRequest changeRequest = em.createQuery(query, ChangeRequest.class)
                    .setParameter("task", task)
                    .setParameter("requester", requester)
                    .getSingleResult();
            return Optional.of(changeRequest);
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    public void save(ChangeRequest changeRequest) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(changeRequest);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<ChangeRequest> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT cr FROM ChangeRequest cr", ChangeRequest.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<ChangeRequest> findByManager(long managerId) {
        EntityManager em = emf.createEntityManager();
        try {
            String query = "SELECT cr FROM ChangeRequest cr WHERE cr.task.createdBy.id = :managerId";
            return em.createQuery(query, ChangeRequest.class)
                    .setParameter("managerId", managerId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void reassignTask(long changeRequestId, long newAssigneeId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            ChangeRequest changeRequest = em.find(ChangeRequest.class, changeRequestId);
            Task task = changeRequest.getTask();
            User newAssignee = em.find(User.class, newAssigneeId);

            task.setAssignedTo(newAssignee);
            em.merge(task);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void deleteChangeRequest(long changeRequestId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            ChangeRequest changeRequest = em.find(ChangeRequest.class, changeRequestId);
            if (changeRequest != null) {
                em.remove(changeRequest);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public ChangeRequest findById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(ChangeRequest.class, id);
        } finally {
            em.close();
        }
    }

    public static void close() {
        if (emf != null) {
            emf.close();
        }
    }
}