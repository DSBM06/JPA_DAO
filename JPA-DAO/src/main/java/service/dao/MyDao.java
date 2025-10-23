package service.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import service.interfaces.ICRUD;
import util.JPAConexion;

import java.util.List;

public class MyDao implements ICRUD {

    @Override
    public <T> List<T> getAll(String nameQuery, Class<T> clazz) {
        EntityManager em = JPAConexion.getEntityManager();
        try {
            TypedQuery<T> query = em.createNamedQuery(nameQuery, clazz);
            return query.getResultList();
        } catch (Exception ex) {ex.printStackTrace();}
        finally {em.close();}
        return null;

    }

    @Override
    public <T> void insert(T entity) {
        EntityManager em = JPAConexion.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public <T> void update(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("update: entity == null");
        }
        EntityManager em = JPAConexion.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback(); // ← ver punto 3
        } finally {
            em.close();
        }
    }

    @Override
    public <T> void delete(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("delete: entity == null");
        }
        EntityManager em = JPAConexion.getEntityManager();
        try {
            em.getTransaction().begin();
            T managed = em.contains(entity) ? entity : em.merge(entity); // nunca merge(null)
            em.remove(managed);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback(); // ← ver punto 3
        } finally {
            em.close();
        }
    }

    @Override
    public <T> T findById(Integer id, Class<T> clazz) {
        EntityManager em = JPAConexion.getEntityManager();
        try {
            T enity = em.find(clazz, id);
            return enity;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }
}
