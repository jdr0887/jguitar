package com.kiluet.jguitar.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Note", propOrder = {})
@XmlRootElement(name = "note")
@Entity
@Table(schema = "jguitar", name = "note")
public class Note extends PersistantEntity {

    private static final long serialVersionUID = 3627594670573036614L;

    @XmlAttribute
    @Column(name = "string", length = 1)
    private Integer string;

    @XmlAttribute
    @Column(name = "value")
    private Integer value;

    @XmlAttribute
    @Column(name = "velocity")
    private Integer velocity;

    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "beat_fid")
    private Beat beat;

    public Note() {
        super();
    }

    public Note(Integer string, Integer value) {
        super();
        this.string = string;
        this.value = value;
    }

    public Integer getString() {
        return string;
    }

    public void setString(Integer string) {
        this.string = string;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getVelocity() {
        return velocity;
    }

    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    public Beat getBeat() {
        return beat;
    }

    public void setBeat(Beat beat) {
        this.beat = beat;
    }

    @Override
    public String toString() {
        return String.format("Note [id=%s, string=%s, value=%s, velocity=%s]", id, string, value, velocity);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((string == null) ? 0 : string.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        result = prime * result + ((velocity == null) ? 0 : velocity.hashCode());
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
        Note other = (Note) obj;
        if (string == null) {
            if (other.string != null)
                return false;
        } else if (!string.equals(other.string))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        if (velocity == null) {
            if (other.velocity != null)
                return false;
        } else if (!velocity.equals(other.velocity))
            return false;
        return true;
    }

}