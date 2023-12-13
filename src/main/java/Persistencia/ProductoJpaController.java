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
import Logica.Producto;
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
public class ProductoJpaController implements Serializable {
    
    public ProductoJpaController(){
        emf=Persistence.createEntityManagerFactory("ferreteriaPU");
    }

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getListaKardex() == null) {
            producto.setListaKardex(new ArrayList<Kardex>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Kardex> attachedListaKardex = new ArrayList<Kardex>();
            for (Kardex listaKardexKardexToAttach : producto.getListaKardex()) {
                listaKardexKardexToAttach = em.getReference(listaKardexKardexToAttach.getClass(), listaKardexKardexToAttach.getId());
                attachedListaKardex.add(listaKardexKardexToAttach);
            }
            producto.setListaKardex(attachedListaKardex);
            em.persist(producto);
            for (Kardex listaKardexKardex : producto.getListaKardex()) {
                Producto oldProductoOfListaKardexKardex = listaKardexKardex.getProducto();
                listaKardexKardex.setProducto(producto);
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

    public void edit(Producto producto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getId());
            List<Kardex> listaKardexOld = persistentProducto.getListaKardex();
            List<Kardex> listaKardexNew = producto.getListaKardex();
            List<Kardex> attachedListaKardexNew = new ArrayList<Kardex>();
            for (Kardex listaKardexNewKardexToAttach : listaKardexNew) {
                listaKardexNewKardexToAttach = em.getReference(listaKardexNewKardexToAttach.getClass(), listaKardexNewKardexToAttach.getId());
                attachedListaKardexNew.add(listaKardexNewKardexToAttach);
            }
            listaKardexNew = attachedListaKardexNew;
            producto.setListaKardex(listaKardexNew);
            producto = em.merge(producto);
            for (Kardex listaKardexOldKardex : listaKardexOld) {
                if (!listaKardexNew.contains(listaKardexOldKardex)) {
                    listaKardexOldKardex.setProducto(null);
                    listaKardexOldKardex = em.merge(listaKardexOldKardex);
                }
            }
            for (Kardex listaKardexNewKardex : listaKardexNew) {
                if (!listaKardexOld.contains(listaKardexNewKardex)) {
                    Producto oldProductoOfListaKardexNewKardex = listaKardexNewKardex.getProducto();
                    listaKardexNewKardex.setProducto(producto);
                    listaKardexNewKardex = em.merge(listaKardexNewKardex);
                    if (oldProductoOfListaKardexNewKardex != null && !oldProductoOfListaKardexNewKardex.equals(producto)) {
                        oldProductoOfListaKardexNewKardex.getListaKardex().remove(listaKardexNewKardex);
                        oldProductoOfListaKardexNewKardex = em.merge(oldProductoOfListaKardexNewKardex);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = producto.getId();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<Kardex> listaKardex = producto.getListaKardex();
            for (Kardex listaKardexKardex : listaKardex) {
                listaKardexKardex.setProducto(null);
                listaKardexKardex = em.merge(listaKardexKardex);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
