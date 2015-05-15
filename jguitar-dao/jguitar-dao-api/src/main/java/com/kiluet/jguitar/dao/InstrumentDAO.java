package com.kiluet.jguitar.dao;

import java.util.List;

import com.kiluet.jguitar.dao.model.Instrument;

public interface InstrumentDAO extends BaseDAO<Instrument, Long> {

    public abstract List<Instrument> findByName(String name) throws JGuitarDAOException;

}
