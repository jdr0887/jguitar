package com.kiluet.jguitar.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kiluet.jguitar.dao.JGuitarDAOException;
import com.kiluet.jguitar.dao.ScaleDAO;
import com.kiluet.jguitar.dao.model.KeyType;
import com.kiluet.jguitar.dao.model.Scale;
import com.kiluet.jguitar.dao.model.ScaleType;
import com.kiluet.jguitar.dao.model.Scale_;

public class ScaleDAOImpl extends BaseDAOImpl<Scale, Long> implements ScaleDAO {

    private final Logger logger = LoggerFactory.getLogger(ScaleDAOImpl.class);

    public ScaleDAOImpl() {
        super();
    }

    @Override
    public Class<Scale> getPersistentClass() {
        return Scale.class;
    }

    @Override
    public List<Scale> findByKeyAndType(KeyType keyType, ScaleType scaleType) throws JGuitarDAOException {
        logger.debug("ENTERING findByKeyAndType(KeyType, ScaleType)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Scale> crit = critBuilder.createQuery(Scale.class);
        Root<Scale> root = crit.from(Scale.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(critBuilder.equal(root.get(Scale_.key), keyType));
        predicates.add(critBuilder.equal(root.get(Scale_.type), scaleType));
        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        crit.orderBy(critBuilder.asc(root.get(Scale_.name)));
        TypedQuery<Scale> query = getEntityManager().createQuery(crit);
        List<Scale> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<Scale> findByExample(Scale scale) throws JGuitarDAOException {
        logger.debug("ENTERING findByExample(Scale)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Scale> crit = critBuilder.createQuery(Scale.class);
        Root<Scale> root = crit.from(Scale.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (StringUtils.isNotEmpty(scale.getName())) {
            predicates.add(critBuilder.equal(root.get(Scale_.name), scale.getName()));
        }
        if (scale.getKey() != null) {
            predicates.add(critBuilder.equal(root.get(Scale_.key), scale.getKey()));
        }
        if (scale.getType() != null) {
            predicates.add(critBuilder.equal(root.get(Scale_.type), scale.getType()));
        }
        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        crit.orderBy(critBuilder.asc(root.get(Scale_.name)));
        TypedQuery<Scale> query = getEntityManager().createQuery(crit);
        List<Scale> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<Scale> findByName(String name) throws JGuitarDAOException {
        logger.debug("ENTERING findByName(String)");
        TypedQuery<Scale> query = getEntityManager().createNamedQuery("Scale.findByName", Scale.class);
        query.setParameter("name", name);
        List<Scale> itemList = query.getResultList();
        return itemList;
    }

    @Override
    public List<Scale> findAll() throws JGuitarDAOException {
        logger.debug("ENTERING findAll()");
        TypedQuery<Scale> query = getEntityManager().createNamedQuery("Scale.findAll", Scale.class);
        List<Scale> ret = query.getResultList();
        return ret;
    }

}
