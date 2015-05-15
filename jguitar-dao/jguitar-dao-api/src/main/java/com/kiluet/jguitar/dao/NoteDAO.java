package com.kiluet.jguitar.dao;

import java.util.List;

import com.kiluet.jguitar.dao.model.Note;

public interface NoteDAO extends BaseDAO<Note, Long> {

    public abstract List<Note> findByBeatId(Long beatId) throws JGuitarDAOException;

}
