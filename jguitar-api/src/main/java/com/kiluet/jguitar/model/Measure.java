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
@XmlType(name = "Measure", propOrder = {})
@XmlRootElement(name = "measure")
public class Measure implements Serializable {

    private static final long serialVersionUID = -8979175208508024995L;

    private Integer number;

    private Long start;

    private TimeSignature timeSignature;

    private Tempo tempo;

    private Marker marker;

    private Boolean repeatOpen;

    private Integer repeatAlternative;

    private Integer repeatClose;

    private Integer tripletFeel;

    @XmlElementWrapper(name = "beats")
    @XmlElement(name = "beat")
    private List<Beat> beats;

    public Measure() {
        super();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public TimeSignature getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(TimeSignature timeSignature) {
        this.timeSignature = timeSignature;
    }

    public Tempo getTempo() {
        return tempo;
    }

    public void setTempo(Tempo tempo) {
        this.tempo = tempo;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Boolean getRepeatOpen() {
        return repeatOpen;
    }

    public void setRepeatOpen(Boolean repeatOpen) {
        this.repeatOpen = repeatOpen;
    }

    public Integer getRepeatAlternative() {
        return repeatAlternative;
    }

    public void setRepeatAlternative(Integer repeatAlternative) {
        this.repeatAlternative = repeatAlternative;
    }

    public Integer getRepeatClose() {
        return repeatClose;
    }

    public void setRepeatClose(Integer repeatClose) {
        this.repeatClose = repeatClose;
    }

    public Integer getTripletFeel() {
        return tripletFeel;
    }

    public void setTripletFeel(Integer tripletFeel) {
        this.tripletFeel = tripletFeel;
    }

    public List<Beat> getBeats() {
        return beats;
    }

    public void setBeats(List<Beat> beats) {
        this.beats = beats;
    }

    @Override
    public String toString() {
        return String
                .format("Measure [number=%s, start=%s, marker=%s, repeatOpen=%s, repeatAlternative=%s, repeatClose=%s, tripletFeel=%s]",
                        number, start, marker, repeatOpen, repeatAlternative, repeatClose, tripletFeel);
    }

}
