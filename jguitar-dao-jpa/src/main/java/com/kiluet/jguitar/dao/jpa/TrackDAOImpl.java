package com.kiluet.jguitar.dao.jpa;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.TrackDAO;
import com.kiluet.jguitar.dao.model.Track;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Singleton
@Transactional(readOnly = true)
@Slf4j
@NoArgsConstructor
public class TrackDAOImpl extends BaseDAOImpl<Track, Long> implements TrackDAO {

    @Override
    public Class<Track> getPersistentClass() {
        return Track.class;
    }

    @Override
    public List<Track> findBySongId(Long songId) throws JGuitarDAOException {
        log.debug("ENTERING findBySongId(Long)");
        TypedQuery<Track> query = getEntityManager().createNamedQuery("Track.findBySongId", Track.class);
        query.setParameter("songId", songId);
        List<Track> itemList = query.getResultList();
        return itemList;
    }

}
