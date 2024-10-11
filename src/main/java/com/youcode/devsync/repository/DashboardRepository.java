package com.youcode.devsync.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class DashboardRepository {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");

    private int getCount(String query) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> typedQuery = em.createQuery(query, Long.class);
            return typedQuery.getSingleResult().intValue();
        } finally {
            em.close();
        }
    }

    public int getTotalUsers() {
        return getCount("SELECT COUNT(u) FROM User u");
    }

    public int getTotalManagers() {
        return getCount("SELECT COUNT(u) FROM User u WHERE u.userRole = 'MANAGER'");
    }

    public int getTotalTasks() {
        return getCount("SELECT COUNT(t) FROM Task t");
    }

    public int getCompletedTasks() {
        return getCount("SELECT COUNT(t) FROM Task t WHERE t.status = 'DONE'");
    }

    public int getNonCompletedTasks() {
        return getCount("SELECT COUNT(t) FROM Task t WHERE t.overdue = true");
    }


}