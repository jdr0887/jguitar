package com.kiluet.jguitar.dao.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Beat", propOrder = {})
@XmlRootElement(name = "beat")
@Entity
@Table(schema = "jguitar", name = "beat")
@NamedQueries({
        @NamedQuery(name = "Beat.findByMeasureId", query = "SELECT a FROM Beat a where a.measure.id = :measureId order by a.number") })
public class Beat extends PersistantEntity {

    private static final long serialVersionUID = -8296658472802163357L;

    @XmlAttribute
    @Column(name = "number", length = 2)
    private Integer number;

    @XmlAttribute
    @Column(name = "duration_type")
    @Enumerated(EnumType.STRING)
    private DurationType duration;

    @XmlElementWrapper(name = "notes")
    @XmlElement(name = "note")
    @OneToMany(mappedBy = "beat", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @OrderBy("string")
    private List<Note> notes;

    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "measure_fid")
    private Measure measure;

    public Beat() {
        super();
    }

    public Beat(Measure measure, DurationType duration, Integer number) {
        super();
        this.measure = measure;
        this.duration = duration;
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void incrementNumber() {
        number++;
    }

    public void decrementNumber() {
        number--;
    }

    public DurationType getDuration() {
        return duration;
    }

    public void setDuration(DurationType duration) {
        this.duration = duration;
    }

    public List<Note> getNotes() {
        if (notes == null) {
            notes = new ArrayList<Note>();
        }
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    @Override
    public String toString() {
        return String.format("Beat [id=%s, number=%s, duration=%s]", id, number, duration);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((duration == null) ? 0 : duration.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
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
        Beat other = (Beat) obj;
        if (duration != other.duration)
            return false;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        return true;
    }

}