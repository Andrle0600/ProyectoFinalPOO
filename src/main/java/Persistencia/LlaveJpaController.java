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
import Logica.Llave;
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
public class LlaveJpaController implements Serializable {
    
    public LlaveJpaController(){
        emf=Persistence.createEntityManagerFactory("ferreteriaPU");
    }

    public LlaveJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Llave llave) {
        if (llave.getListaKardex() == null) {
            llave.setListaKardex(new ArrayList<Kardex>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Kardex> attachedListaKardex = new ArrayList<Kardex>();
            for (Kardex listaKardexKardexToAttach : llave.getListaKardex()) {
                listaKardexKardexToAttach = em.getReference(listaKardexKardexToAttach.getClass(), listaKardexKardexToAttach.getId());
                attachedListaKardex.add(listaKardexKardexToAttach);
            }
            llave.setListaKardex(attachedListaKardex);
            em.persist(llave);
            for (Kardex listaKardexKardex : llave.getListaKardex()) {
                Logica.Producto oldProductoOfListaKardexKardex = listaKardexKardex.getProducto();
                listaKardexKardex.setProducto(llave);
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

    public void edit(Llave llave) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Llave persistentLlave = em.find(Llave.class, llave.getId());
            List<Kardex> listaKardexOld = persistentLlave.getListaKardex();
            List<Kardex> listaKardexNew = llave.getListaKardex();
            List<Kardex> attachedListaKardexNew = new ArrayList<Kardex>();
            for (Kardex listaKardexNewKardexToAttach : listaKardexNew) {
                listaKardexNewKardexToAttach = em.getReference(listaKardexNewKardexToAttach.getClass(), listaKardexNewKardexToAttach.getId());
                attachedListaKardexNew.add(listaKardexNewKardexToAttach);
            }
            listaKardexNew = attachedListaKardexNew;
            llave.setListaKardex(listaKardexNew);
            llave = em.merge(llave);
            for (Kardex listaKardexOldKardex : listaKardexOld) {
                if (!listaKardexNew.contains(listaKardexOldKardex)) {
                    listaKardexOldKardex.setProducto(null);
                    listaKardexOldKardex = em.merge(listaKardexOldKardex);
                }
            }
            for (Kardex listaKardexNewKardex : listaKardexNew) {
                if (!listaKardexOld.contains(listaKardexNewKardex)) {
                    Llave oldProductoOfListaKardexNewKardex = (Llave) listaKardexNewKardex.getProducto();
                    listaKardexNewKardex.setProducto(llave);
                    listaKardexNewKardex = em.merge(listaKardexNewKardex);
                    if (oldProductoOfListaKardexNewKardex != null && !oldProductoOfListaKardexNewKardex.equals(llave)) {
                        oldProductoOfListaKardexNewKardex.getListaKardex().remove(listaKardexNewKardex);
                        oldProductoOfListaKardexNewKardex = em.merge(oldProductoOfListaKardexNewKardex);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = llave.getId();
                if (findLlave(id) == null) {
                    throw new NonexistentEntityException("The llave with id " + id + " no longer exists.");
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
            Llave llave;
            try {
                llave = em.getReference(Llave.class, id);
                llave.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The llave with id " + id + " no longer exists.", enfe);
            }
            List<Kardex> listaKardex = llave.getListaKardex();
            for (Kardex listaKardexKardex : listaKardex) {
                listaKardexKardex.setProducto(null);
                listaKardexKardex = em.merge(listaKardexKardex);
            }
            em.remove(llave);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Llave> findLlaveEntities() {
        return findLlaveEntities(true, -1, -1);
    }

    public List<Llave> findLlaveEntities(int maxResults, int firstResult) {
        return findLlaveEntities(false, maxResults, firstResult);
    }

    private List<Llave> findLlaveEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Llave.class));
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

    public Llave findLlave(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Llave.class, id);
        } finally {
            em.close();
        }
    }

    public int getLlaveCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Llave> rt = cq.from(Llave.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
