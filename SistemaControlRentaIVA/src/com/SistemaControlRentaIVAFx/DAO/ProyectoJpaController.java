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
import com.SistemaControlRentaIVAFx.Entities.Cliente;
import com.SistemaControlRentaIVAFx.Entities.Presupuesto;
import com.SistemaControlRentaIVAFx.Entities.Proyecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author freddy ayala
 */
public class ProyectoJpaController implements Serializable {

    public ProyectoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proyecto proyecto) {
        if (proyecto.getPresupuestoList() == null) {
            proyecto.setPresupuestoList(new ArrayList<Presupuesto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente clienteCliId = proyecto.getClienteCliId();
            if (clienteCliId != null) {
                clienteCliId = em.getReference(clienteCliId.getClass(), clienteCliId.getCliId());
                proyecto.setClienteCliId(clienteCliId);
            }
            List<Presupuesto> attachedPresupuestoList = new ArrayList<Presupuesto>();
            for (Presupuesto presupuestoListPresupuestoToAttach : proyecto.getPresupuestoList()) {
                presupuestoListPresupuestoToAttach = em.getReference(presupuestoListPresupuestoToAttach.getClass(), presupuestoListPresupuestoToAttach.getPreId());
                attachedPresupuestoList.add(presupuestoListPresupuestoToAttach);
            }
            proyecto.setPresupuestoList(attachedPresupuestoList);
            em.persist(proyecto);
            if (clienteCliId != null) {
                clienteCliId.getProyectoList().add(proyecto);
                clienteCliId = em.merge(clienteCliId);
            }
            for (Presupuesto presupuestoListPresupuesto : proyecto.getPresupuestoList()) {
                Proyecto oldProyectoProIdOfPresupuestoListPresupuesto = presupuestoListPresupuesto.getProyectoProId();
                presupuestoListPresupuesto.setProyectoProId(proyecto);
                presupuestoListPresupuesto = em.merge(presupuestoListPresupuesto);
                if (oldProyectoProIdOfPresupuestoListPresupuesto != null) {
                    oldProyectoProIdOfPresupuestoListPresupuesto.getPresupuestoList().remove(presupuestoListPresupuesto);
                    oldProyectoProIdOfPresupuestoListPresupuesto = em.merge(oldProyectoProIdOfPresupuestoListPresupuesto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proyecto proyecto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyecto persistentProyecto = em.find(Proyecto.class, proyecto.getProId());
            Cliente clienteCliIdOld = persistentProyecto.getClienteCliId();
            Cliente clienteCliIdNew = proyecto.getClienteCliId();
            List<Presupuesto> presupuestoListOld = persistentProyecto.getPresupuestoList();
            List<Presupuesto> presupuestoListNew = proyecto.getPresupuestoList();
            List<String> illegalOrphanMessages = null;
            for (Presupuesto presupuestoListOldPresupuesto : presupuestoListOld) {
                if (!presupuestoListNew.contains(presupuestoListOldPresupuesto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Presupuesto " + presupuestoListOldPresupuesto + " since its proyectoProId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteCliIdNew != null) {
                clienteCliIdNew = em.getReference(clienteCliIdNew.getClass(), clienteCliIdNew.getCliId());
                proyecto.setClienteCliId(clienteCliIdNew);
            }
            List<Presupuesto> attachedPresupuestoListNew = new ArrayList<Presupuesto>();
            for (Presupuesto presupuestoListNewPresupuestoToAttach : presupuestoListNew) {
                presupuestoListNewPresupuestoToAttach = em.getReference(presupuestoListNewPresupuestoToAttach.getClass(), presupuestoListNewPresupuestoToAttach.getPreId());
                attachedPresupuestoListNew.add(presupuestoListNewPresupuestoToAttach);
            }
            presupuestoListNew = attachedPresupuestoListNew;
            proyecto.setPresupuestoList(presupuestoListNew);
            proyecto = em.merge(proyecto);
            if (clienteCliIdOld != null && !clienteCliIdOld.equals(clienteCliIdNew)) {
                clienteCliIdOld.getProyectoList().remove(proyecto);
                clienteCliIdOld = em.merge(clienteCliIdOld);
            }
            if (clienteCliIdNew != null && !clienteCliIdNew.equals(clienteCliIdOld)) {
                clienteCliIdNew.getProyectoList().add(proyecto);
                clienteCliIdNew = em.merge(clienteCliIdNew);
            }
            for (Presupuesto presupuestoListNewPresupuesto : presupuestoListNew) {
                if (!presupuestoListOld.contains(presupuestoListNewPresupuesto)) {
                    Proyecto oldProyectoProIdOfPresupuestoListNewPresupuesto = presupuestoListNewPresupuesto.getProyectoProId();
                    presupuestoListNewPresupuesto.setProyectoProId(proyecto);
                    presupuestoListNewPresupuesto = em.merge(presupuestoListNewPresupuesto);
                    if (oldProyectoProIdOfPresupuestoListNewPresupuesto != null && !oldProyectoProIdOfPresupuestoListNewPresupuesto.equals(proyecto)) {
                        oldProyectoProIdOfPresupuestoListNewPresupuesto.getPresupuestoList().remove(presupuestoListNewPresupuesto);
                        oldProyectoProIdOfPresupuestoListNewPresupuesto = em.merge(oldProyectoProIdOfPresupuestoListNewPresupuesto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proyecto.getProId();
                if (findProyecto(id) == null) {
                    throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.");
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
            Proyecto proyecto;
            try {
                proyecto = em.getReference(Proyecto.class, id);
                proyecto.getProId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Presupuesto> presupuestoListOrphanCheck = proyecto.getPresupuestoList();
            for (Presupuesto presupuestoListOrphanCheckPresupuesto : presupuestoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the Presupuesto " + presupuestoListOrphanCheckPresupuesto + " in its presupuestoList field has a non-nullable proyectoProId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente clienteCliId = proyecto.getClienteCliId();
            if (clienteCliId != null) {
                clienteCliId.getProyectoList().remove(proyecto);
                clienteCliId = em.merge(clienteCliId);
            }
            em.remove(proyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proyecto> findProyectoEntities() {
        return findProyectoEntities(true, -1, -1);
    }

    public List<Proyecto> findProyectoEntities(int maxResults, int firstResult) {
        return findProyectoEntities(false, maxResults, firstResult);
    }

    private List<Proyecto> findProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proyecto.class));
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

    public Proyecto findProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proyecto> rt = cq.from(Proyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
