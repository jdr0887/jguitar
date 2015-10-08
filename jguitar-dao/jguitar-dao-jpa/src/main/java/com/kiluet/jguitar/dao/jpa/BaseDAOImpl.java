package com.kiluet.jguitar.dao.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.BaseDAO;
import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.Persistable;

public abstract class BaseDAOImpl<T extends Persistable, ID extends Serializable> implements BaseDAO<T, ID> {

    private final Logger logger = LoggerFactory.getLogger(BaseDAOImpl.class);

    @PersistenceUnit(name = "jguitar", unitName = "jguitar")
    private EntityManager entityManager;

    public BaseDAOImpl() {
        super();
    }

    public abstract Class<T> getPersistentClass();

    @Override
    public synchronized Long save(T entity) throws JGuitarDAOException {
        logger.debug("ENTERING save(T)");
        entityManager.getTransaction().begin();
        if (!entityManager.contains(entity) && entity.getId() != null) {
            entity = entityManager.merge(entity);
        } else {
            entityManager.persist(entity);
        }
        entityManager.flush();
        entityManager.getTransaction().commit();
        return entity.getId();
    }

    @Override
    public synchronized void delete(T entity) throws JGuitarDAOException {
        logger.debug("ENTERING delete(T)");
        entityManager.getTransaction().begin();
        T foundEntity = entityManager.find(getPersistentClass(), entity.getId());
        entityManager.remove(foundEntity);
        entityManager.getTransaction().commit();
    }

    @Override
    public synchronized void delete(List<T> entityList) throws JGuitarDAOException {
        List<Long> idList = new ArrayList<Long>();
        for (T t : entityList) {
            idList.add(t.getId());
        }
        entityManager.getTransaction().begin();
        Query qDelete = entityManager
                .createQuery("delete from " + getPersistentClass().getSimpleName() + " a where a.id in (?1)");
        qDelete.setParameter(1, idList);
        qDelete.executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    public synchronized T findById(ID id) throws JGuitarDAOException {
        logger.debug("ENTERING findById(T)");
        T ret = entityManager.find(getPersistentClass(), id);
        return ret;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
