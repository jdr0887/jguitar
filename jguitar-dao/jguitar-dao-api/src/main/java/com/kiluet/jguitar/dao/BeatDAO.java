package com.kiluet.jguitar.dao;

import java.util.List;

import com.kiluet.jguitar.dao.model.Beat;

public interface BeatDAO extends BaseDAO<Beat, Long> {

    public abstract List<Beat> findByMeasureId(Long measureId) throws JGuitarDAOException;

    public abstract List<Beat> findBySongIdAndMeasureNumber(Long songId, Integer measureNumber)
            throws JGuitarDAOException;

}
