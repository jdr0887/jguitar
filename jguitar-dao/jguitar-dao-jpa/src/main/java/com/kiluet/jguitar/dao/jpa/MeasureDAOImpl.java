package com.kiluet.jguitar.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.MeasureDAO;
import com.kiluet.jguitar.dao.model.Measure;

public class MeasureDAOImpl extends BaseDAOImpl<Measure, Long> implements MeasureDAO {

    private final Logger logger = LoggerFactory.getLogger(MeasureDAOImpl.class);

    public MeasureDAOImpl() {
        super();
    }

    @Override
    public Class<Measure> getPersistentClass() {
        return Measure.class;
    }

    @Override
    public List<Measure> findByTrackId(Long trackId) throws JGuitarDAOException {
        logger.debug("ENTERING findByTrackId(Long)");
        TypedQuery<Measure> query = getEntityManager().createNamedQuery("Measure.findByTrackId", Measure.class);
        query.setParameter("trackId", trackId);
        List<Measure> itemList = query.getResultList();
        return itemList;
    }

}
