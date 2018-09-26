package com.kiluet.jguitar.dao;

import java.util.List;

import com.kiluet.jguitar.dao.model.KeyType;
import com.kiluet.jguitar.dao.model.Scale;
import com.kiluet.jguitar.dao.model.ScaleType;

public interface ScaleDAO extends BaseDAO<Scale, Long> {

    public abstract List<Scale> findByExample(Scale scale) throws JGuitarDAOException;

    public abstract List<Scale> findByKeyAndType(KeyType keyType, ScaleType scaleType) throws JGuitarDAOException;

    public abstract List<Scale> findByName(String name) throws JGuitarDAOException;

    public abstract List<Scale> findAll() throws JGuitarDAOException;

}
