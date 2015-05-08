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
@XmlType(name = "Beat", propOrder = {})
@XmlRootElement(name = "beat")
public class Beat implements Serializable {

    private static final long serialVersionUID = -8296658472802163357L;

    @XmlAttribute
    private DurationType type;

    private Long start;

    private Chord chord;

    private Text text;

    @XmlElementWrapper(name = "notes")
    @XmlElement(name = "note")
    private List<Note> notes;

    private Stroke stroke;

    public Beat() {
        super();
    }

    public Beat(DurationType type) {
        super();
        this.type = type;
    }

    public DurationType getType() {
        return type;
    }

    public void setType(DurationType type) {
        this.type = type;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Chord getChord() {
        return chord;
    }

    public void setChord(Chord chord) {
        this.chord = chord;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public List<Note> getNotes() {
        if (notes == null) {
            notes = new LinkedList<Note>();
        }
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    @Override
    public String toString() {
        return String
                .format("Beat [type=%s, start=%s, chord=%s, text=%s, stroke=%s]", type, start, chord, text, stroke);
    }

}