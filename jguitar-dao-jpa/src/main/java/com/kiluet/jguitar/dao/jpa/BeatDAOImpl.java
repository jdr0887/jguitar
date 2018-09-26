package com.kiluet.jguitar.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kiluet.jguitar.dao.BeatDAO;
import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.Beat_;
import com.kiluet.jguitar.dao.model.Measure;
import com.kiluet.jguitar.dao.model.Measure_;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.dao.model.Song_;
import com.kiluet.jguitar.dao.model.Track;
import com.kiluet.jguitar.dao.model.Track_;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Singleton
@Transactional(readOnly = true)
@Slf4j
@NoArgsConstructor
public class BeatDAOImpl extends BaseDAOImpl<Beat, Long> implements BeatDAO {

    @Override
    public Class<Beat> getPersistentClass() {
        return Beat.class;
    }

    @Override
    public List<Beat> findByMeasureId(Long measureId) throws JGuitarDAOException {
        log.debug("ENTERING findByMeasureId(Long)");
        TypedQuery<Beat> query = getEntityManager().createNamedQuery("Beat.findByMeasureId", Beat.class);
        query.setParameter("measureId", measureId);
        List<Beat> itemList = query.getResultList();
        return itemList;
    }

    @Override
    public List<Beat> findBySongIdAndMeasureNumber(Long songId, Integer measureNumber) throws JGuitarDAOException {
        log.debug("ENTERING findBySongIdAndMeasureNumber(Long, Integer)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Beat> crit = critBuilder.createQuery(Beat.class);
        Root<Beat> root = crit.from(Beat.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        Join<Beat, Measure> beatMeasureJoin = root.join(Beat_.measure);
        predicates.add(critBuilder.equal(beatMeasureJoin.get(Measure_.number), measureNumber));

        Join<Measure, Track> measureTrackJoin = beatMeasureJoin.join(Measure_.track);
        Join<Track, Song> trackSongJoin = measureTrackJoin.join(Track_.song);
        predicates.add(critBuilder.equal(trackSongJoin.get(Song_.id), songId));

        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        // crit.groupBy(measureTrackJoin.get(Track_.number));
        TypedQuery<Beat> query = getEntityManager().createQuery(crit);
        List<Beat> ret = query.getResultList();
        return ret;
    }

}
