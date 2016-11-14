/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SistemaControlRentaIVAFx.DAO;

import com.SistemaControlRentaIVAFx.DAO.exceptions.IllegalOrphanException;
import com.SistemaControlRentaIVAFx.DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.SistemaControlRentaIVAFx.Entities.Iva;
import com.SistemaControlRentaIVAFx.Entities.Renta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author freddy ayala
 */
public class RentaJpaController implements Serializable {

    public RentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Renta renta) {
        if (renta.getIvaList() == null) {
            renta.setIvaList(new ArrayList<Iva>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Iva> attachedIvaList = new ArrayList<Iva>();
            for (Iva ivaListIvaToAttach : renta.getIvaList()) {
                ivaListIvaToAttach = em.getReference(ivaListIvaToAttach.getClass(), ivaListIvaToAttach.getIvaId());
                attachedIvaList.add(ivaListIvaToAttach);
            }
            renta.setIvaList(attachedIvaList);
            em.persist(renta);
            for (Iva ivaListIva : renta.getIvaList()) {
                Renta oldRentaRenIdOfIvaListIva = ivaListIva.getRentaRenId();
                ivaListIva.setRentaRenId(renta);
                ivaListIva = em.merge(ivaListIva);
                if (oldRentaRenIdOfIvaListIva != null) {
                    oldRentaRenIdOfIvaListIva.getIvaList().remove(ivaListIva);
                    oldRentaRenIdOfIvaListIva = em.merge(oldRentaRenIdOfIvaListIva);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Renta renta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Renta persistentRenta = em.find(Renta.class, renta.getRenId());
            List<Iva> ivaListOld = persistentRenta.getIvaList();
            List<Iva> ivaListNew = renta.getIvaList();
            List<String> illegalOrphanMessages = null;
            for (Iva ivaListOldIva : ivaListOld) {
                if (!ivaListNew.contains(ivaListOldIva)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Iva " + ivaListOldIva + " since its rentaRenId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Iva> attachedIvaListNew = new ArrayList<Iva>();
            for (Iva ivaListNewIvaToAttach : ivaListNew) {
                ivaListNewIvaToAttach = em.getReference(ivaListNewIvaToAttach.getClass(), ivaListNewIvaToAttach.getIvaId());
                attachedIvaListNew.add(ivaListNewIvaToAttach);
            }
            ivaListNew = attachedIvaListNew;
            renta.setIvaList(ivaListNew);
            renta = em.merge(renta);
            for (Iva ivaListNewIva : ivaListNew) {
                if (!ivaListOld.contains(ivaListNewIva)) {
                    Renta oldRentaRenIdOfIvaListNewIva = ivaListNewIva.getRentaRenId();
                    ivaListNewIva.setRentaRenId(renta);
                    ivaListNewIva = em.merge(ivaListNewIva);
                    if (oldRentaRenIdOfIvaListNewIva != null && !oldRentaRenIdOfIvaListNewIva.equals(renta)) {
                        oldRentaRenIdOfIvaListNewIva.getIvaList().remove(ivaListNewIva);
                        oldRentaRenIdOfIvaListNewIva = em.merge(oldRentaRenIdOfIvaListNewIva);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = renta.getRenId();
                if (findRenta(id) == null) {
                    throw new NonexistentEntityException("The renta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Renta renta;
            try {
                renta = em.getReference(Renta.class, id);
                renta.getRenId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The renta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Iva> ivaListOrphanCheck = renta.getIvaList();
            for (Iva ivaListOrphanCheckIva : ivaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Renta (" + renta + ") cannot be destroyed since the Iva " + ivaListOrphanCheckIva + " in its ivaList field has a non-nullable rentaRenId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(renta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Renta> findRentaEntities() {
        return findRentaEntities(true, -1, -1);
    }

    public List<Renta> findRentaEntities(int maxResults, int firstResult) {
        return findRentaEntities(false, maxResults, firstResult);
    }

    private List<Renta> findRentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Renta.class));
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

    public Renta findRenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Renta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Renta> rt = cq.from(Renta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
