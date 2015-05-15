package com.kiluet.jguitar.dao.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.openjpa.persistence.jdbc.Index;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Instrument", propOrder = {})
@XmlRootElement(name = "instrument")
@Entity
@Table(schema = "jguitar", name = "instrument", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
@NamedQueries({ @NamedQuery(name = "Instrument.findByName", query = "SELECT a FROM Instrument a where a.name = :name") })
public class Instrument extends PersistantEntity {

    private static final long serialVersionUID = 860249427816538695L;

    @XmlAttribute
    @Index
    @Column(name = "name")
    private String name;

    @XmlAttribute
    @Column(name = "program")
    private Integer program;

    @XmlElementWrapper(name = "strings")
    @XmlElement(name = "string")
    @OneToMany(mappedBy = "instrument", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    private List<InstrumentString> strings;

    public Instrument() {
        super();
    }

    public Instrument(String name, Integer program) {
        super();
        this.name = name;
        this.program = program;
    }

    public Integer getProgram() {
        return program;
    }

    public void setProgram(Integer program) {
        this.program = program;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<InstrumentString> getStrings() {
        if (strings == null) {
            strings = new ArrayList<InstrumentString>();
        }
        return strings;
    }

    public void setStrings(List<InstrumentString> strings) {
        this.strings = strings;
    }

    @Override
    public String toString() {
        return String.format("Instrument [id=%s, name=%s, program=%s]", id, name, program);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((program == null) ? 0 : program.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Instrument other = (Instrument) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (program == null) {
            if (other.program != null)
                return false;
        } else if (!program.equals(other.program))
            return false;
        return true;
    }

}
