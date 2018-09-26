package com.kiluet.jguitar.dao.jpa;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kiluet.jguitar.dao.InstrumentDAO;
import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.model.Instrument;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Singleton
@Transactional(readOnly = true)
@Slf4j
@NoArgsConstructor
public class InstrumentDAOImpl extends BaseDAOImpl<Instrument, Long> implements InstrumentDAO {

    @Override
    public Class<Instrument> getPersistentClass() {
        return Instrument.class;
    }

    @Override
    public List<Instrument> findAll() throws JGuitarDAOException {
        log.debug("ENTERING findAll()");
        TypedQuery<Instrument> query = getEntityManager().createNamedQuery("Instrument.findAll", Instrument.class);
        List<Instrument> itemList = query.getResultList();
        return itemList;
    }

    @Override
    public List<Instrument> findByName(String name) throws JGuitarDAOException {
        log.debug("ENTERING findByName(String)");
        TypedQuery<Instrument> query = getEntityManager().createNamedQuery("Instrument.findByName", Instrument.class);
        query.setParameter("name", name);
        List<Instrument> itemList = query.getResultList();
        return itemList;
    }

    @Override
    public Instrument findByProgram(Integer program) throws JGuitarDAOException {
        log.debug("ENTERING findByProgram(Integer)");
        TypedQuery<Instrument> query = getEntityManager().createNamedQuery("Instrument.findByProgram", Instrument.class);
        query.setParameter("program", program);
        List<Instrument> itemList = query.getResultList();
        if (CollectionUtils.isNotEmpty(itemList)) {
            return itemList.get(0);
        }
        return null;
    }

}
