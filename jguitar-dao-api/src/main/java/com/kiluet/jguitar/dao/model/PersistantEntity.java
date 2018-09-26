package com.kiluet.jguitar.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.kiluet.jguitar.dao.Persistable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString(includeFieldNames = true)
@MappedSuperclass
public class PersistantEntity<T extends Serializable> implements Persistable<T> {

    private static final long serialVersionUID = 4002519233115451788L;

    @Id()
    @GeneratedValue
    @Column(name = "id")
    protected T id;

}
