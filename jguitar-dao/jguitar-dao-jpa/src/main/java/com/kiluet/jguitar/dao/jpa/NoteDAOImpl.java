package com.kiluet.jguitar.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.NoteDAO;
import com.kiluet.jguitar.dao.model.Note;

public class NoteDAOImpl extends BaseDAOImpl<Note, Long> implements NoteDAO {

    private final Logger logger = LoggerFactory.getLogger(NoteDAOImpl.class);

    public NoteDAOImpl() {
        super();
    }

    @Override
    public Class<Note> getPersistentClass() {
        return Note.class;
    }

    @Override
    public List<Note> findByBeatId(Long beatId) throws JGuitarDAOException {
        logger.info("ENTERING findByBeatId(Long)");
        TypedQuery<Note> query = getEntityManager().createNamedQuery("Note.findByBeatId", Note.class);
        query.setParameter("beatId", beatId);
        List<Note> itemList = query.getResultList();
        return itemList;
    }

}
