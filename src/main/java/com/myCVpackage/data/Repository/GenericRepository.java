package com.myCVpackage.data.Repository;

import com.myCVpackage.Controller.IController;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


/**
 * Created by buresina on 21/10/2016.
 */
public class GenericRepository<T> implements IController<T> {

    public static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("msg_cv");
    public static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private Class<T> clazz;

    public GenericRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T findById(int id){

        return entityManager.find(clazz, id);
    }

    public List<T> findAll(){

        CriteriaQuery<T> criteria = entityManager.getCriteriaBuilder().createQuery(clazz);
        criteria.select(criteria.from(clazz));
        return entityManager.createQuery(criteria).getResultList();
    }

    public List<T> findByColumn(String column, String property) throws Exception{
        
        String q = "select t from " + clazz.getSimpleName() + " t where t." + column + "='" + property + "'";
        Query query = entityManager.createQuery(q, clazz);
        return query.getResultList();

    }

    public void save(T entity) throws Exception{
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(entity);
        entityManager.getTransaction().commit();

    }
}
