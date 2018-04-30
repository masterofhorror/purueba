package co.com.runt.sagir.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.logging.Logger;

/**
 *
 * @author rhernandez
 * @param <T>
 */
public class GenericDAO<T> {

    private static final Logger LOGGER = Logger.getLogger(GenericDAO.class.getSimpleName());

    @PersistenceContext(unitName = "sagirDS")
    EntityManager em;

    /**
     *
     */
    protected Class<T> persistentClass;

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public GenericDAO() {
        //Metodo vacio requerido
    }

    /**
     *
     * @param pojo
     * @return
     */
    public T add(T pojo) {
        em.persist(pojo);
        return pojo;
    }

    /**
     *
     * @param pojo
     * @return
     */
    public T update(T pojo) {
        em.merge(pojo);
        em.flush();
        return pojo;
    }

    /**
     *
     * @param pojo
     */
    public void delete(T pojo) {
        em.remove(em.contains(pojo) ? pojo : em.merge(pojo));
        em.flush();
    }

    /**
     *
     * @param id
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public T findByPK(String id) {
        return em.find(persistentClass, id);
    }

    /**
     *
     * @param id
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public T findByPK(Long id) {
        return em.find(persistentClass, id);
    }

    /**
     *
     * @param id
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public T findByPK(Short id) {
        return em.find(persistentClass, id);
    }
    
    /**
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List<T> findAll(){       
        List list =  em.createNamedQuery(persistentClass.getSimpleName()+".findAll" ).getResultList();
        return list;
    }

    /**
     *
     * @param pojoList
     */
    public void deleteMultiple(List<T> pojoList) {
        for (T pojo : pojoList) {
            em.remove(pojo);
        }
        em.flush();
    }

    /**
     *
     * @return
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     *
     * @param em
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

}
