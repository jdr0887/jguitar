package com.kiluet.jguitar.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Track", propOrder = {})
@XmlRootElement(name = "track")
public class Track implements Serializable {

    private static final long serialVersionUID = -705819738289324321L;

    @XmlAttribute
    private Integer number;

    private Integer offset;

    private Integer channelId;

    @XmlAttribute
    private Boolean solo;

    @XmlAttribute
    private Boolean mute;

    private InstrumentType instrument;

    @XmlElementWrapper(name = "measures")
    @XmlElement(name = "measure")
    private List<Measure> measures;

    private String color;

    private Lyric lyrics;

    public Track() {
        super();
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

    public InstrumentType getInstrument() {
        return instrument;
    }

    public void setInstrument(InstrumentType instrument) {
        this.instrument = instrument;
    }

    public List<Measure> getMeasures() {
        if (measures == null) {
            measures = new LinkedList<Measure>();
        }
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Lyric getLyrics() {
        return lyrics;
    }

    public void setLyrics(Lyric lyrics) {
        this.lyrics = lyrics;
    }

    @Override
    public String toString() {
        return String.format(
                "Track [number=%s, offset=%s, channelId=%s, solo=%s, mute=%s, instrument=%s, color=%s, lyrics=%s]",
                number, offset, channelId, solo, mute, instrument, color, lyrics);
    }

}
