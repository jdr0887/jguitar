package com.kiluet.jguitar.dao.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@XmlType(name = "Track", propOrder = {})
@XmlRootElement(name = "track")
@Entity
@Table(schema = "jguitar", name = "track")
@NamedQueries({
        @NamedQuery(name = "Track.findBySongId", query = "SELECT a FROM Track a where a.song.id = :songId order by a.number") })
public class Track extends PersistantEntity {

    private static final long serialVersionUID = -705819738289324321L;

    @XmlAttribute
    @Column(name = "number")
    private Integer number;

    @Column(name = "offset")
    private Integer offset;

    @Column(name = "channel_id")
    private Integer channelId;

    @XmlAttribute
    @Column(name = "solo")
    private Boolean solo;

    @XmlAttribute
    private Boolean mute;

    @ManyToOne
    @JoinColumn(name = "instrument_fid")
    private Instrument instrument;

    @Column(name = "color")
    private String color;

    @XmlElementWrapper(name = "measures")
    @XmlElement(name = "measure")
    @OneToMany(mappedBy = "track", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @OrderBy("number")
    private List<Measure> measures;

    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "song_fid")
    private Song song;

    public Track() {
        super();
    }

    public Track(Song song, Instrument instrument, Integer number) {
        super();
        this.song = song;
        this.instrument = instrument;
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Boolean getSolo() {
        return solo;
    }

    public void setSolo(Boolean solo) {
        this.solo = solo;
    }

    public Boolean getMute() {
        return mute;
    }

    public void setMute(Boolean mute) {
        this.mute = mute;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Measure> getMeasures() {
        if (measures == null) {
            measures = new ArrayList<Measure>();
        }
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    @Override
    public String toString() {
        return String.format(
                "Track [id=%s, number=%s, offset=%s, channelId=%s, solo=%s, mute=%s, instrument=%s, color=%s]", id,
                number, offset, channelId, solo, mute, instrument, color);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((channelId == null) ? 0 : channelId.hashCode());
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((instrument == null) ? 0 : instrument.hashCode());
        result = prime * result + ((mute == null) ? 0 : mute.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result + ((offset == null) ? 0 : offset.hashCode());
        result = prime * result + ((solo == null) ? 0 : solo.hashCode());
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
        Track other = (Track) obj;
        if (channelId == null) {
            if (other.channelId != null)
                return false;
        } else if (!channelId.equals(other.channelId))
            return false;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        if (instrument != other.instrument)
            return false;
        if (mute == null) {
            if (other.mute != null)
                return false;
        } else if (!mute.equals(other.mute))
            return false;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        if (offset == null) {
            if (other.offset != null)
                return false;
        } else if (!offset.equals(other.offset))
            return false;
        if (solo == null) {
            if (other.solo != null)
                return false;
        } else if (!solo.equals(other.solo))
            return false;
        return true;
    }

}
