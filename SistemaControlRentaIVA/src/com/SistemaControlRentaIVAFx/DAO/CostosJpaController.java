/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SistemaControlRentaIVAFx.DAO;

import com.SistemaControlRentaIVAFx.DAO.exceptions.NonexistentEntityException;
import com.SistemaControlRentaIVAFx.Entities.Costos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.SistemaControlRentaIVAFx.Entities.Iva;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author freddy ayala
 */
public class CostosJpaController implements Serializable {

    public CostosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Costos costos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Iva ivaIvaId = costos.getIvaIvaId();
            if (ivaIvaId != null) {
                ivaIvaId = em.getReference(ivaIvaId.getClass(), ivaIvaId.getIvaId());
                costos.setIvaIvaId(ivaIvaId);
            }
            em.persist(costos);
            if (ivaIvaId != null) {
                ivaIvaId.getCostosList().add(costos);
                ivaIvaId = em.merge(ivaIvaId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Costos costos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Costos persistentCostos = em.find(Costos.class, costos.getCosId());
            Iva ivaIvaIdOld = persistentCostos.getIvaIvaId();
            Iva ivaIvaIdNew = costos.getIvaIvaId();
            if (ivaIvaIdNew != null) {
                ivaIvaIdNew = em.getReference(ivaIvaIdNew.getClass(), ivaIvaIdNew.getIvaId());
                costos.setIvaIvaId(ivaIvaIdNew);
            }
            costos = em.merge(costos);
            if (ivaIvaIdOld != null && !ivaIvaIdOld.equals(ivaIvaIdNew)) {
                ivaIvaIdOld.getCostosList().remove(costos);
                ivaIvaIdOld = em.merge(ivaIvaIdOld);
            }
            if (ivaIvaIdNew != null && !ivaIvaIdNew.equals(ivaIvaIdOld)) {
                ivaIvaIdNew.getCostosList().add(costos);
                ivaIvaIdNew = em.merge(ivaIvaIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = costos.getCosId();
                if (findCostos(id) == null) {
                    throw new NonexistentEntityException("The costos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Costos costos;
            try {
                costos = em.getReference(Costos.class, id);
                costos.getCosId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The costos with id " + id + " no longer exists.", enfe);
            }
            Iva ivaIvaId = costos.getIvaIvaId();
            if (ivaIvaId != null) {
                ivaIvaId.getCostosList().remove(costos);
                ivaIvaId = em.merge(ivaIvaId);
            }
            em.remove(costos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Costos> findCostosEntities() {
        return findCostosEntities(true, -1, -1);
    }

    public List<Costos> findCostosEntities(int maxResults, int firstResult) {
        return findCostosEntities(false, maxResults, firstResult);
    }

    private List<Costos> findCostosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Costos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Costos findCostos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Costos.class, id);
        } finally {
            em.close();
        }
    }

    public int getCostosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Costos> rt = cq.from(Costos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
