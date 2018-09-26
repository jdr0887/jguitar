package com.kiluet.jguitar.dao;

import java.util.List;

import com.kiluet.jguitar.dao.model.Song;

public interface SongDAO extends BaseDAO<Song, Long> {

    public abstract List<Song> findByTitle(String title) throws JGuitarDAOException;

    public abstract List<Song> findAll() throws JGuitarDAOException;

}
