package com.kiluet.jguitar.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.NoteDAO;
import com.kiluet.jguitar.dao.model.Beat;
import com.kiluet.jguitar.dao.model.Beat_;
import com.kiluet.jguitar.dao.model.Note;
import com.kiluet.jguitar.dao.model.Note_;

public class NoteDAOImpl extends BaseDAOImpl<Note, Long>implements NoteDAO {

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
        logger.debug("ENTERING findByBeatId(Long)");
        TypedQuery<Note> query = getEntityManager().createNamedQuery("Note.findByBeatId", Note.class);
        query.setParameter("beatId", beatId);
        List<Note> itemList = query.getResultList();
        return itemList;
    }

    @Override
    public Note findByBeatIdAndString(Long beatId, Integer string) throws JGuitarDAOException {
        logger.debug("ENTERING findByBeatIdAndString(Long, Integer)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Note> crit = critBuilder.createQuery(Note.class);
        Root<Note> root = crit.from(Note.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(critBuilder.equal(root.get(Note_.string), string));

        Join<Note, Beat> noteBeatJoin = root.join(Note_.beat);
        predicates.add(critBuilder.equal(noteBeatJoin.get(Beat_.id), beatId));

        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<Note> query = getEntityManager().createQuery(crit);
        List<Note> ret = query.getResultList();
        if (CollectionUtils.isNotEmpty(ret)) {
            return ret.get(0);
        }
        return null;
    }

}
