/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Kardex;
import Logica.Tuerca;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author 15-CW1024
 */
public class TuercaJpaController implements Serializable {
    
    public TuercaJpaController(){
        emf=Persistence.createEntityManagerFactory("ferreteriaPU");
    }

    public TuercaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tuerca tuerca) {
        if (tuerca.getListaKardex() == null) {
            tuerca.setListaKardex(new ArrayList<Kardex>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Kardex> attachedListaKardex = new ArrayList<Kardex>();
            for (Kardex listaKardexKardexToAttach : tuerca.getListaKardex()) {
                listaKardexKardexToAttach = em.getReference(listaKardexKardexToAttach.getClass(), listaKardexKardexToAttach.getId());
                attachedListaKardex.add(listaKardexKardexToAttach);
            }
            tuerca.setListaKardex(attachedListaKardex);
            em.persist(tuerca);
            for (Kardex listaKardexKardex : tuerca.getListaKardex()) {
                Logica.Producto oldProductoOfListaKardexKardex = listaKardexKardex.getProducto();
                listaKardexKardex.setProducto(tuerca);
                listaKardexKardex = em.merge(listaKardexKardex);
                if (oldProductoOfListaKardexKardex != null) {
                    oldProductoOfListaKardexKardex.getListaKardex().remove(listaKardexKardex);
                    oldProductoOfListaKardexKardex = em.merge(oldProductoOfListaKardexKardex);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tuerca tuerca) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tuerca persistentTuerca = em.find(Tuerca.class, tuerca.getId());
            List<Kardex> listaKardexOld = persistentTuerca.getListaKardex();
            List<Kardex> listaKardexNew = tuerca.getListaKardex();
            List<Kardex> attachedListaKardexNew = new ArrayList<Kardex>();
            for (Kardex listaKardexNewKardexToAttach : listaKardexNew) {
                listaKardexNewKardexToAttach = em.getReference(listaKardexNewKardexToAttach.getClass(), listaKardexNewKardexToAttach.getId());
                attachedListaKardexNew.add(listaKardexNewKardexToAttach);
            }
            listaKardexNew = attachedListaKardexNew;
            tuerca.setListaKardex(listaKardexNew);
            tuerca = em.merge(tuerca);
            for (Kardex listaKardexOldKardex : listaKardexOld) {
                if (!listaKardexNew.contains(listaKardexOldKardex)) {
                    listaKardexOldKardex.setProducto(null);
                    listaKardexOldKardex = em.merge(listaKardexOldKardex);
                }
            }
            for (Kardex listaKardexNewKardex : listaKardexNew) {
                if (!listaKardexOld.contains(listaKardexNewKardex)) {
                    Tuerca oldProductoOfListaKardexNewKardex = (Tuerca) listaKardexNewKardex.getProducto();
                    listaKardexNewKardex.setProducto(tuerca);
                    listaKardexNewKardex = em.merge(listaKardexNewKardex);
                    if (oldProductoOfListaKardexNewKardex != null && !oldProductoOfListaKardexNewKardex.equals(tuerca)) {
                        oldProductoOfListaKardexNewKardex.getListaKardex().remove(listaKardexNewKardex);
                        oldProductoOfListaKardexNewKardex = em.merge(oldProductoOfListaKardexNewKardex);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = tuerca.getId();
                if (findTuerca(id) == null) {
                    throw new NonexistentEntityException("The tuerca with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tuerca tuerca;
            try {
                tuerca = em.getReference(Tuerca.class, id);
                tuerca.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tuerca with id " + id + " no longer exists.", enfe);
            }
            List<Kardex> listaKardex = tuerca.getListaKardex();
            for (Kardex listaKardexKardex : listaKardex) {
                listaKardexKardex.setProducto(null);
                listaKardexKardex = em.merge(listaKardexKardex);
            }
            em.remove(tuerca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tuerca> findTuercaEntities() {
        return findTuercaEntities(true, -1, -1);
    }

    public List<Tuerca> findTuercaEntities(int maxResults, int firstResult) {
        return findTuercaEntities(false, maxResults, firstResult);
    }

    private List<Tuerca> findTuercaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tuerca.class));
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

    public Tuerca findTuerca(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tuerca.class, id);
        } finally {
            em.close();
        }
    }

    public int getTuercaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tuerca> rt = cq.from(Tuerca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
