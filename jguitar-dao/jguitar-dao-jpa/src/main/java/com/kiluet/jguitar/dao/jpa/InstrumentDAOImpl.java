package com.kiluet.jguitar.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kiluet.jguitar.dao.InstrumentDAO;
import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.model.Instrument;

@Component
@Transactional(readOnly = true)
public class InstrumentDAOImpl extends BaseDAOImpl<Instrument, Long>implements InstrumentDAO {

    private final Logger logger = LoggerFactory.getLogger(InstrumentDAOImpl.class);

    public InstrumentDAOImpl() {
        super();
    }

    @Override
    public Class<Instrument> getPersistentClass() {
        return Instrument.class;
    }

    @Override
    public List<Instrument> findAll() throws JGuitarDAOException {
        logger.debug("ENTERING findAll()");
        TypedQuery<Instrument> query = getEntityManager().createNamedQuery("Instrument.findAll", Instrument.class);
        List<Instrument> itemList = query.getResultList();
        return itemList;
    }

    @Override
    public List<Instrument> findByName(String name) throws JGuitarDAOException {
        logger.debug("ENTERING findByName(String)");
        TypedQuery<Instrument> query = getEntityManager().createNamedQuery("Instrument.findByName", Instrument.class);
        query.setParameter("name", name);
        List<Instrument> itemList = query.getResultList();
        return itemList;
    }

    @Override
    public Instrument findByProgram(Integer program) throws JGuitarDAOException {
        logger.debug("ENTERING findByProgram(Integer)");
        TypedQuery<Instrument> query = getEntityManager().createNamedQuery("Instrument.findByProgram",
                Instrument.class);
        query.setParameter("program", program);
        List<Instrument> itemList = query.getResultList();
        if (CollectionUtils.isNotEmpty(itemList)) {
            return itemList.get(0);
        }
        return null;
    }

}
