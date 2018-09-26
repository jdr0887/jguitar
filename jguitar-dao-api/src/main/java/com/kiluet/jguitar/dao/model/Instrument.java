package com.kiluet.jguitar.dao.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema = "jguitar", name = "instrument", indexes = { @Index(columnList = "name", unique = true) })
@NamedQueries({ @NamedQuery(name = "Instrument.findByName", query = "SELECT a FROM Instrument a where a.name = :name order by a.name"),
        @NamedQuery(name = "Instrument.findByProgram", query = "SELECT a FROM Instrument a where a.program = :program"),
        @NamedQuery(name = "Instrument.findAll", query = "SELECT a FROM Instrument a order by a.name") })
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, includeFieldNames = true)
public class Instrument extends PersistantEntity<Long> {

    private static final long serialVersionUID = 860249427816538695L;

    @Column(name = "name")
    private String name;

    @Column(name = "program")
    private Integer program;

    @OneToMany(mappedBy = "instrument", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @OrderBy("string")
    private List<InstrumentString> strings;

    public Instrument(String name, Integer program) {
        super();
        this.name = name;
        this.program = program;
    }

}
