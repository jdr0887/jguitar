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
@XmlType(name = "Measure", propOrder = {})
@XmlRootElement(name = "measure")
@Entity
@Table(schema = "jguitar", name = "measure")
@NamedQueries({
        @NamedQuery(name = "Measure.findByTrackId", query = "SELECT a FROM Measure a where a.track.id = :trackId order by a.number") })
public class Measure extends PersistantEntity {

    private static final long serialVersionUID = -8979175208508024995L;

    @XmlAttribute
    @Column(name = "number")
    private Integer number;

    @Column(name = "start")
    private Long start;

    @XmlAttribute
    @Column(name = "meter_type")
    @Enumerated(EnumType.STRING)
    private MeterType meterType;

    @Column(name = "tempo")
    private Integer tempo;

    @Column(name = "open_repeat")
    private Boolean openRepeat = Boolean.FALSE;

    @Column(name = "alternate_repeat")
    private Integer alternativeRepeat;

    @Column(name = "close_repeat")
    private Integer closeRepeat;

    @Column(name = "triplet_feel")
    private Integer tripletFeel;

    @XmlElementWrapper(name = "beats")
    @XmlElement(name = "beat")
    @OneToMany(mappedBy = "measure", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private List<Beat> beats;

    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "track_fid")
    private Track track;

    public Measure() {
        super();
    }

    public Measure(Measure measure) {
        super();
        this.track = measure.getTrack();
        this.meterType = measure.getMeterType();
        this.tempo = measure.getTempo();
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

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public MeterType getMeterType() {
        return meterType;
    }

    public void setMeterType(MeterType meterType) {
        this.meterType = meterType;
    }

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public Boolean getOpenRepeat() {
        return openRepeat;
    }

    public void setOpenRepeat(Boolean openRepeat) {
        this.openRepeat = openRepeat;
    }

    public Integer getAlternativeRepeat() {
        return alternativeRepeat;
    }

    public void setAlternativeRepeat(Integer alternativeRepeat) {
        this.alternativeRepeat = alternativeRepeat;
    }

    public Integer getCloseRepeat() {
        return closeRepeat;
    }

    public void setCloseRepeat(Integer closeRepeat) {
        this.closeRepeat = closeRepeat;
    }

    public Integer getTripletFeel() {
        return tripletFeel;
    }

    public void setTripletFeel(Integer tripletFeel) {
        this.tripletFeel = tripletFeel;
    }

    public List<Beat> getBeats() {
        if (beats == null) {
            beats = new ArrayList<Beat>();
        }
        return beats;
    }

    public void setBeats(List<Beat> beats) {
        this.beats = beats;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    @Override
    public String toString() {
        return String.format(
                "Measure [id=%s, number=%s, start=%s, meterType=%s, tempo=%s, openRepeat=%s, alternativeRepeat=%s, closeRepeat=%s, tripletFeel=%s]",
                id, number, start, meterType, tempo, openRepeat, alternativeRepeat, closeRepeat, tripletFeel);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((alternativeRepeat == null) ? 0 : alternativeRepeat.hashCode());
        result = prime * result + ((closeRepeat == null) ? 0 : closeRepeat.hashCode());
        result = prime * result + ((meterType == null) ? 0 : meterType.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result + ((openRepeat == null) ? 0 : openRepeat.hashCode());
        result = prime * result + ((start == null) ? 0 : start.hashCode());
        result = prime * result + ((tempo == null) ? 0 : tempo.hashCode());
        result = prime * result + ((tripletFeel == null) ? 0 : tripletFeel.hashCode());
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
        Measure other = (Measure) obj;
        if (alternativeRepeat == null) {
            if (other.alternativeRepeat != null)
                return false;
        } else if (!alternativeRepeat.equals(other.alternativeRepeat))
            return false;
        if (closeRepeat == null) {
            if (other.closeRepeat != null)
                return false;
        } else if (!closeRepeat.equals(other.closeRepeat))
            return false;
        if (meterType != other.meterType)
            return false;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        if (openRepeat == null) {
            if (other.openRepeat != null)
                return false;
        } else if (!openRepeat.equals(other.openRepeat))
            return false;
        if (start == null) {
            if (other.start != null)
                return false;
        } else if (!start.equals(other.start))
            return false;
        if (tempo == null) {
            if (other.tempo != null)
                return false;
        } else if (!tempo.equals(other.tempo))
            return false;
        if (tripletFeel == null) {
            if (other.tripletFeel != null)
                return false;
        } else if (!tripletFeel.equals(other.tripletFeel))
            return false;
        return true;
    }

}
