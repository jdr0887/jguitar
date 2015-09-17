package com.kiluet.jguitar.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.BeatDAO;
import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.model.Beat;

public class BeatDAOImpl extends BaseDAOImpl<Beat, Long> implements BeatDAO {

    private final Logger logger = LoggerFactory.getLogger(BeatDAOImpl.class);

    public BeatDAOImpl() {
        super();
    }

    @Override
    public Class<Beat> getPersistentClass() {
        return Beat.class;
    }

    @Override
    public List<Beat> findByMeasureId(Long measureId) throws JGuitarDAOException {
        logger.debug("ENTERING findByMeasureId(Long)");
        TypedQuery<Beat> query = getEntityManager().createNamedQuery("Beat.findByMeasureId", Beat.class);
        query.setParameter("measureId", measureId);
        List<Beat> itemList = query.getResultList();
        return itemList;
    }

}
