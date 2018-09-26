package com.kiluet.jguitar.dao;

import java.util.List;

import com.kiluet.jguitar.dao.model.Track;

public interface TrackDAO extends BaseDAO<Track, Long> {

    public abstract List<Track> findBySongId(Long songId) throws JGuitarDAOException;

}
