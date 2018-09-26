package com.kiluet.jguitar.dao.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kiluet.jguitar.dao.BaseDAO;
import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.Persistable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Repository
@Singleton
@Slf4j
@NoArgsConstructor
@Getter
@Setter
public abstract class BaseDAOImpl<T extends Persistable<ID>, ID extends Serializable> implements BaseDAO<T, ID> {

    @PersistenceContext
    private EntityManager entityManager;

    public abstract Class<T> getPersistentClass();

    @Override
    public ID save(T entity) throws JGuitarDAOException {
        log.debug("ENTERING save(T)");
        synchronized (entityManager) {
            if (entity == null) {
                log.error("entity is null");
                return null;
            }
            if (!getEntityManager().contains(entity) && entity.getId() != null) {
                entity = getEntityManager().merge(entity);
            } else {
                getEntityManager().persist(entity);
            }
        }
        return entity.getId();
    }

    @Override
    public void delete(T entity) throws JGuitarDAOException {
        log.debug("ENTERING delete(T)");
        T foundEntity = entityManager.find(getPersistentClass(), entity.getId());
        entityManager.remove(foundEntity);
    }

    @Override
    public void delete(List<T> entityList) throws JGuitarDAOException {
        List<ID> idList = new ArrayList<>();
        for (T t : entityList) {
            idList.add(t.getId());
        }
        Query qDelete = entityManager.createQuery("delete from " + getPersistentClass().getSimpleName() + " a where a.id in (?1)");
        qDelete.setParameter(1, idList);
        qDelete.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(ID id) throws JGuitarDAOException {
        log.debug("ENTERING findById(T)");
        T ret = entityManager.find(getPersistentClass(), id);
        return ret;
    }

}
