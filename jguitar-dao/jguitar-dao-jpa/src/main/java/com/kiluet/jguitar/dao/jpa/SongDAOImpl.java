package com.kiluet.jguitar.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.SongDAO;
import com.kiluet.jguitar.dao.model.Song;
import com.kiluet.jguitar.dao.model.Song_;

@Component
@Transactional(readOnly = true)
public class SongDAOImpl extends BaseDAOImpl<Song, Long> implements SongDAO {

    private final Logger logger = LoggerFactory.getLogger(SongDAOImpl.class);

    public SongDAOImpl() {
        super();
    }

    @Override
    public Class<Song> getPersistentClass() {
        return Song.class;
    }

    @Override
    public List<Song> findByTitle(String title) throws JGuitarDAOException {
        logger.debug("ENTERING findByTitle(String)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Song> crit = critBuilder.createQuery(Song.class);
        Root<Song> root = crit.from(Song.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(critBuilder.like(root.get(Song_.title), title));
        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        crit.orderBy(critBuilder.asc(root.get(Song_.title)));
        TypedQuery<Song> query = getEntityManager().createQuery(crit);
        List<Song> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<Song> findAll() throws JGuitarDAOException {
        logger.debug("ENTERING findAll()");
        TypedQuery<Song> query = getEntityManager().createNamedQuery("Song.findAll", Song.class);
        List<Song> ret = query.getResultList();
        return ret;
    }

}
