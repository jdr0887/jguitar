package com.kiluet.jguitar.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.TrackDAO;
import com.kiluet.jguitar.dao.model.Track;

public class TrackDAOImpl extends BaseDAOImpl<Track, Long> implements TrackDAO {

    private final Logger logger = LoggerFactory.getLogger(TrackDAOImpl.class);

    public TrackDAOImpl() {
        super();
    }

    @Override
    public Class<Track> getPersistentClass() {
        return Track.class;
    }

    @Override
    public List<Track> findBySongId(Long songId) throws JGuitarDAOException {
        logger.debug("ENTERING findBySongId(Long)");
        TypedQuery<Track> query = getEntityManager().createNamedQuery("Track.findBySongId", Track.class);
        query.setParameter("songId", songId);
        List<Track> itemList = query.getResultList();
        return itemList;
    }

}
