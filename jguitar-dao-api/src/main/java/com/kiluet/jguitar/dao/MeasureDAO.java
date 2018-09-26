package com.kiluet.jguitar.dao;

import java.util.List;

import com.kiluet.jguitar.dao.model.Measure;

public interface MeasureDAO extends BaseDAO<Measure, Long> {

    public abstract List<Measure> findByTrackId(Long trackId) throws JGuitarDAOException;

}
