package com.kiluet.jguitar.dao.jpa;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.MeasureDAO;
import com.kiluet.jguitar.dao.model.Measure;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Singleton
@Transactional(readOnly = true)
@Slf4j
@NoArgsConstructor
public class MeasureDAOImpl extends BaseDAOImpl<Measure, Long> implements MeasureDAO {

    @Override
    public Class<Measure> getPersistentClass() {
        return Measure.class;
    }

    @Override
    public List<Measure> findByTrackId(Long trackId) throws JGuitarDAOException {
        log.debug("ENTERING findByTrackId(Long)");
        TypedQuery<Measure> query = getEntityManager().createNamedQuery("Measure.findByTrackId", Measure.class);
        query.setParameter("trackId", trackId);
        List<Measure> itemList = query.getResultList();
        return itemList;
    }

}
