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
import com.SistemaControlRentaIVAFx.Entities.Renta;
import com.SistemaControlRentaIVAFx.Entities.Presupuesto;
import java.util.ArrayList;
import java.util.List;
import com.SistemaControlRentaIVAFx.Entities.Costos;
import com.SistemaControlRentaIVAFx.Entities.Iva;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author freddy ayala
 */
public class IvaJpaController implements Serializable {

    public IvaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Iva iva) {
        if (iva.getPresupuestoList() == null) {
            iva.setPresupuestoList(new ArrayList<Presupuesto>());
        }
        if (iva.getCostosList() == null) {
            iva.setCostosList(new ArrayList<Costos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Renta rentaRenId = iva.getRentaRenId();
            if (rentaRenId != null) {
                rentaRenId = em.getReference(rentaRenId.getClass(), rentaRenId.getRenId());
                iva.setRentaRenId(rentaRenId);
            }
            List<Presupuesto> attachedPresupuestoList = new ArrayList<Presupuesto>();
            for (Presupuesto presupuestoListPresupuestoToAttach : iva.getPresupuestoList()) {
                presupuestoListPresupuestoToAttach = em.getReference(presupuestoListPresupuestoToAttach.getClass(), presupuestoListPresupuestoToAttach.getPreId());
                attachedPresupuestoList.add(presupuestoListPresupuestoToAttach);
            }
            iva.setPresupuestoList(attachedPresupuestoList);
            List<Costos> attachedCostosList = new ArrayList<Costos>();
            for (Costos costosListCostosToAttach : iva.getCostosList()) {
                costosListCostosToAttach = em.getReference(costosListCostosToAttach.getClass(), costosListCostosToAttach.getCosId());
                attachedCostosList.add(costosListCostosToAttach);
            }
            iva.setCostosList(attachedCostosList);
            em.persist(iva);
            if (rentaRenId != null) {
                rentaRenId.getIvaList().add(iva);
                rentaRenId = em.merge(rentaRenId);
            }
            for (Presupuesto presupuestoListPresupuesto : iva.getPresupuestoList()) {
                Iva oldIvaIvaIdOfPresupuestoListPresupuesto = presupuestoListPresupuesto.getIvaIvaId();
                presupuestoListPresupuesto.setIvaIvaId(iva);
                presupuestoListPresupuesto = em.merge(presupuestoListPresupuesto);
                if (oldIvaIvaIdOfPresupuestoListPresupuesto != null) {
                    oldIvaIvaIdOfPresupuestoListPresupuesto.getPresupuestoList().remove(presupuestoListPresupuesto);
                    oldIvaIvaIdOfPresupuestoListPresupuesto = em.merge(oldIvaIvaIdOfPresupuestoListPresupuesto);
                }
            }
            for (Costos costosListCostos : iva.getCostosList()) {
                Iva oldIvaIvaIdOfCostosListCostos = costosListCostos.getIvaIvaId();
                costosListCostos.setIvaIvaId(iva);
                costosListCostos = em.merge(costosListCostos);
                if (oldIvaIvaIdOfCostosListCostos != null) {
                    oldIvaIvaIdOfCostosListCostos.getCostosList().remove(costosListCostos);
                    oldIvaIvaIdOfCostosListCostos = em.merge(oldIvaIvaIdOfCostosListCostos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Iva iva) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Iva persistentIva = em.find(Iva.class, iva.getIvaId());
            Renta rentaRenIdOld = persistentIva.getRentaRenId();
            Renta rentaRenIdNew = iva.getRentaRenId();
            List<Presupuesto> presupuestoListOld = persistentIva.getPresupuestoList();
            List<Presupuesto> presupuestoListNew = iva.getPresupuestoList();
            List<Costos> costosListOld = persistentIva.getCostosList();
            List<Costos> costosListNew = iva.getCostosList();
            List<String> illegalOrphanMessages = null;
            for (Presupuesto presupuestoListOldPresupuesto : presupuestoListOld) {
                if (!presupuestoListNew.contains(presupuestoListOldPresupuesto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Presupuesto " + presupuestoListOldPresupuesto + " since its ivaIvaId field is not nullable.");
                }
            }
            for (Costos costosListOldCostos : costosListOld) {
                if (!costosListNew.contains(costosListOldCostos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Costos " + costosListOldCostos + " since its ivaIvaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (rentaRenIdNew != null) {
                rentaRenIdNew = em.getReference(rentaRenIdNew.getClass(), rentaRenIdNew.getRenId());
                iva.setRentaRenId(rentaRenIdNew);
            }
            List<Presupuesto> attachedPresupuestoListNew = new ArrayList<Presupuesto>();
            for (Presupuesto presupuestoListNewPresupuestoToAttach : presupuestoListNew) {
                presupuestoListNewPresupuestoToAttach = em.getReference(presupuestoListNewPresupuestoToAttach.getClass(), presupuestoListNewPresupuestoToAttach.getPreId());
                attachedPresupuestoListNew.add(presupuestoListNewPresupuestoToAttach);
            }
            presupuestoListNew = attachedPresupuestoListNew;
            iva.setPresupuestoList(presupuestoListNew);
            List<Costos> attachedCostosListNew = new ArrayList<Costos>();
            for (Costos costosListNewCostosToAttach : costosListNew) {
                costosListNewCostosToAttach = em.getReference(costosListNewCostosToAttach.getClass(), costosListNewCostosToAttach.getCosId());
                attachedCostosListNew.add(costosListNewCostosToAttach);
            }
            costosListNew = attachedCostosListNew;
            iva.setCostosList(costosListNew);
            iva = em.merge(iva);
            if (rentaRenIdOld != null && !rentaRenIdOld.equals(rentaRenIdNew)) {
                rentaRenIdOld.getIvaList().remove(iva);
                rentaRenIdOld = em.merge(rentaRenIdOld);
            }
            if (rentaRenIdNew != null && !rentaRenIdNew.equals(rentaRenIdOld)) {
                rentaRenIdNew.getIvaList().add(iva);
                rentaRenIdNew = em.merge(rentaRenIdNew);
            }
            for (Presupuesto presupuestoListNewPresupuesto : presupuestoListNew) {
                if (!presupuestoListOld.contains(presupuestoListNewPresupuesto)) {
                    Iva oldIvaIvaIdOfPresupuestoListNewPresupuesto = presupuestoListNewPresupuesto.getIvaIvaId();
                    presupuestoListNewPresupuesto.setIvaIvaId(iva);
                    presupuestoListNewPresupuesto = em.merge(presupuestoListNewPresupuesto);
                    if (oldIvaIvaIdOfPresupuestoListNewPresupuesto != null && !oldIvaIvaIdOfPresupuestoListNewPresupuesto.equals(iva)) {
                        oldIvaIvaIdOfPresupuestoListNewPresupuesto.getPresupuestoList().remove(presupuestoListNewPresupuesto);
                        oldIvaIvaIdOfPresupuestoListNewPresupuesto = em.merge(oldIvaIvaIdOfPresupuestoListNewPresupuesto);
                    }
                }
            }
            for (Costos costosListNewCostos : costosListNew) {
                if (!costosListOld.contains(costosListNewCostos)) {
                    Iva oldIvaIvaIdOfCostosListNewCostos = costosListNewCostos.getIvaIvaId();
                    costosListNewCostos.setIvaIvaId(iva);
                    costosListNewCostos = em.merge(costosListNewCostos);
                    if (oldIvaIvaIdOfCostosListNewCostos != null && !oldIvaIvaIdOfCostosListNewCostos.equals(iva)) {
                        oldIvaIvaIdOfCostosListNewCostos.getCostosList().remove(costosListNewCostos);
                        oldIvaIvaIdOfCostosListNewCostos = em.merge(oldIvaIvaIdOfCostosListNewCostos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = iva.getIvaId();
                if (findIva(id) == null) {
                    throw new NonexistentEntityException("The iva with id " + id + " no longer exists.");
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
            Iva iva;
            try {
                iva = em.getReference(Iva.class, id);
                iva.getIvaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The iva with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Presupuesto> presupuestoListOrphanCheck = iva.getPresupuestoList();
            for (Presupuesto presupuestoListOrphanCheckPresupuesto : presupuestoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Iva (" + iva + ") cannot be destroyed since the Presupuesto " + presupuestoListOrphanCheckPresupuesto + " in its presupuestoList field has a non-nullable ivaIvaId field.");
            }
            List<Costos> costosListOrphanCheck = iva.getCostosList();
            for (Costos costosListOrphanCheckCostos : costosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Iva (" + iva + ") cannot be destroyed since the Costos " + costosListOrphanCheckCostos + " in its costosList field has a non-nullable ivaIvaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Renta rentaRenId = iva.getRentaRenId();
            if (rentaRenId != null) {
                rentaRenId.getIvaList().remove(iva);
                rentaRenId = em.merge(rentaRenId);
            }
            em.remove(iva);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Iva> findIvaEntities() {
        return findIvaEntities(true, -1, -1);
    }

    public List<Iva> findIvaEntities(int maxResults, int firstResult) {
        return findIvaEntities(false, maxResults, firstResult);
    }

    private List<Iva> findIvaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Iva.class));
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

    public Iva findIva(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Iva.class, id);
        } finally {
            em.close();
        }
    }

    public int getIvaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Iva> rt = cq.from(Iva.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
