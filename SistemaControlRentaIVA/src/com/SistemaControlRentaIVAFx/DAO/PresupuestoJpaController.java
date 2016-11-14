/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SistemaControlRentaIVAFx.DAO;

import com.SistemaControlRentaIVAFx.DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.SistemaControlRentaIVAFx.Entities.Proyecto;
import com.SistemaControlRentaIVAFx.Entities.Iva;
import com.SistemaControlRentaIVAFx.Entities.Presupuesto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author freddy ayala
 */
public class PresupuestoJpaController implements Serializable {

    public PresupuestoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Presupuesto presupuesto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyecto proyectoProId = presupuesto.getProyectoProId();
            if (proyectoProId != null) {
                proyectoProId = em.getReference(proyectoProId.getClass(), proyectoProId.getProId());
                presupuesto.setProyectoProId(proyectoProId);
            }
            Iva ivaIvaId = presupuesto.getIvaIvaId();
            if (ivaIvaId != null) {
                ivaIvaId = em.getReference(ivaIvaId.getClass(), ivaIvaId.getIvaId());
                presupuesto.setIvaIvaId(ivaIvaId);
            }
            em.persist(presupuesto);
            if (proyectoProId != null) {
                proyectoProId.getPresupuestoList().add(presupuesto);
                proyectoProId = em.merge(proyectoProId);
            }
            if (ivaIvaId != null) {
                ivaIvaId.getPresupuestoList().add(presupuesto);
                ivaIvaId = em.merge(ivaIvaId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Presupuesto presupuesto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presupuesto persistentPresupuesto = em.find(Presupuesto.class, presupuesto.getPreId());
            Proyecto proyectoProIdOld = persistentPresupuesto.getProyectoProId();
            Proyecto proyectoProIdNew = presupuesto.getProyectoProId();
            Iva ivaIvaIdOld = persistentPresupuesto.getIvaIvaId();
            Iva ivaIvaIdNew = presupuesto.getIvaIvaId();
            if (proyectoProIdNew != null) {
                proyectoProIdNew = em.getReference(proyectoProIdNew.getClass(), proyectoProIdNew.getProId());
                presupuesto.setProyectoProId(proyectoProIdNew);
            }
            if (ivaIvaIdNew != null) {
                ivaIvaIdNew = em.getReference(ivaIvaIdNew.getClass(), ivaIvaIdNew.getIvaId());
                presupuesto.setIvaIvaId(ivaIvaIdNew);
            }
            presupuesto = em.merge(presupuesto);
            if (proyectoProIdOld != null && !proyectoProIdOld.equals(proyectoProIdNew)) {
                proyectoProIdOld.getPresupuestoList().remove(presupuesto);
                proyectoProIdOld = em.merge(proyectoProIdOld);
            }
            if (proyectoProIdNew != null && !proyectoProIdNew.equals(proyectoProIdOld)) {
                proyectoProIdNew.getPresupuestoList().add(presupuesto);
                proyectoProIdNew = em.merge(proyectoProIdNew);
            }
            if (ivaIvaIdOld != null && !ivaIvaIdOld.equals(ivaIvaIdNew)) {
                ivaIvaIdOld.getPresupuestoList().remove(presupuesto);
                ivaIvaIdOld = em.merge(ivaIvaIdOld);
            }
            if (ivaIvaIdNew != null && !ivaIvaIdNew.equals(ivaIvaIdOld)) {
                ivaIvaIdNew.getPresupuestoList().add(presupuesto);
                ivaIvaIdNew = em.merge(ivaIvaIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = presupuesto.getPreId();
                if (findPresupuesto(id) == null) {
                    throw new NonexistentEntityException("The presupuesto with id " + id + " no longer exists.");
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
            Presupuesto presupuesto;
            try {
                presupuesto = em.getReference(Presupuesto.class, id);
                presupuesto.getPreId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The presupuesto with id " + id + " no longer exists.", enfe);
            }
            Proyecto proyectoProId = presupuesto.getProyectoProId();
            if (proyectoProId != null) {
                proyectoProId.getPresupuestoList().remove(presupuesto);
                proyectoProId = em.merge(proyectoProId);
            }
            Iva ivaIvaId = presupuesto.getIvaIvaId();
            if (ivaIvaId != null) {
                ivaIvaId.getPresupuestoList().remove(presupuesto);
                ivaIvaId = em.merge(ivaIvaId);
            }
            em.remove(presupuesto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Presupuesto> findPresupuestoEntities() {
        return findPresupuestoEntities(true, -1, -1);
    }

    public List<Presupuesto> findPresupuestoEntities(int maxResults, int firstResult) {
        return findPresupuestoEntities(false, maxResults, firstResult);
    }

    private List<Presupuesto> findPresupuestoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Presupuesto.class));
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

    public Presupuesto findPresupuesto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Presupuesto.class, id);
        } finally {
            em.close();
        }
    }

    public int getPresupuestoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Presupuesto> rt = cq.from(Presupuesto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
