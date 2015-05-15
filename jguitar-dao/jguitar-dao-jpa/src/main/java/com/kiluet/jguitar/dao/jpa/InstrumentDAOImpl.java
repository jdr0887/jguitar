package com.kiluet.jguitar.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.InstrumentDAO;
import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.model.Instrument;

public class InstrumentDAOImpl extends BaseDAOImpl<Instrument, Long> implements InstrumentDAO {

    private final Logger logger = LoggerFactory.getLogger(InstrumentDAOImpl.class);

    public InstrumentDAOImpl() {
        super();
    }

    @Override
    public Class<Instrument> getPersistentClass() {
        return Instrument.class;
    }

    @Override
    public List<Instrument> findByName(String name) throws JGuitarDAOException {
        logger.info("ENTERING findByName(String)");
        TypedQuery<Instrument> query = getEntityManager().createNamedQuery("Instrument.findByName", Instrument.class);
        query.setParameter("name", name);
        List<Instrument> itemList = query.getResultList();
        return itemList;
    }

}
