package com.kiluet.jguitar.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDAO<T extends Persistable<ID>, ID extends Serializable> {

    public abstract ID save(T entity) throws JGuitarDAOException;

    public abstract void delete(T entity) throws JGuitarDAOException;

    public abstract void delete(List<T> idList) throws JGuitarDAOException;

    public abstract T findById(ID id) throws JGuitarDAOException;

}
