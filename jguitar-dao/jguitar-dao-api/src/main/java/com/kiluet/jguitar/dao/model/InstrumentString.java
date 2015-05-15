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
@XmlType(name = "InstrumentString", propOrder = {})
@XmlRootElement(name = "instrumentString")
@Entity
@Table(schema = "jguitar", name = "instrument_string")
public class InstrumentString extends PersistantEntity {

    private static final long serialVersionUID = -3772337130346447202L;

    @XmlAttribute
    @Column(name = "string")
    private Integer string;

    @XmlAttribute
    @Column(name = "pitch")
    private Integer pitch;

    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "instrument_fid")
    private Instrument instrument;

    public InstrumentString() {
        super();
    }

    public InstrumentString(Integer string, Integer pitch, Instrument instrument) {
        super();
        this.string = string;
        this.pitch = pitch;
        this.instrument = instrument;
    }

    public Integer getString() {
        return string;
    }

    public void setString(Integer string) {
        this.string = string;
    }

    public Integer getPitch() {
        return pitch;
    }

    public void setPitch(Integer pitch) {
        this.pitch = pitch;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    @Override
    public String toString() {
        return String.format("InstrumentString [id=%s, string=%s, pitch=%s]", id, string, pitch);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((string == null) ? 0 : string.hashCode());
        result = prime * result + ((pitch == null) ? 0 : pitch.hashCode());
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
        InstrumentString other = (InstrumentString) obj;
        if (string == null) {
            if (other.string != null)
                return false;
        } else if (!string.equals(other.string))
            return false;
        if (pitch == null) {
            if (other.pitch != null)
                return false;
        } else if (!pitch.equals(other.pitch))
            return false;
        return true;
    }

}
