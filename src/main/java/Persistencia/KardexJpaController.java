/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Logica.Kardex;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Producto;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author 15-CW1024
 */
public class KardexJpaController implements Serializable {
    
    public KardexJpaController(){
        emf=Persistence.createEntityManagerFactory("ferreteriaPU");
    }

    public KardexJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Kardex kardex) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto = kardex.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getId());
                kardex.setProducto(producto);
            }
            em.persist(kardex);
            if (producto != null) {
                producto.getListaKardex().add(kardex);
                producto = em.merge(producto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Kardex kardex) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kardex persistentKardex = em.find(Kardex.class, kardex.getId());
            Producto productoOld = persistentKardex.getProducto();
            Producto productoNew = kardex.getProducto();
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getId());
                kardex.setProducto(productoNew);
            }
            kardex = em.merge(kardex);
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getListaKardex().remove(kardex);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getListaKardex().add(kardex);
                productoNew = em.merge(productoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = kardex.getId();
                if (findKardex(id) == null) {
                    throw new NonexistentEntityException("The kardex with id " + id + " no longer exists.");
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
            Kardex kardex;
            try {
                kardex = em.getReference(Kardex.class, id);
                kardex.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kardex with id " + id + " no longer exists.", enfe);
            }
            Producto producto = kardex.getProducto();
            if (producto != null) {
                producto.getListaKardex().remove(kardex);
                producto = em.merge(producto);
            }
            em.remove(kardex);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kardex> findKardexEntities() {
        return findKardexEntities(true, -1, -1);
    }

    public List<Kardex> findKardexEntities(int maxResults, int firstResult) {
        return findKardexEntities(false, maxResults, firstResult);
    }

    private List<Kardex> findKardexEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kardex.class));
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

    public Kardex findKardex(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kardex.class, id);
        } finally {
            em.close();
        }
    }

    public int getKardexCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kardex> rt = cq.from(Kardex.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
