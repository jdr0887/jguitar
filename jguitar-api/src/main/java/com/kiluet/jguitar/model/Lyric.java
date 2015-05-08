package com.kiluet.jguitar.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Lyric", propOrder = {})
@XmlRootElement(name = "lyric")
public class Lyric {

    private Integer from;

    private InstrumentType lyrics;

    public Lyric() {
        super();
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public InstrumentType getLyrics() {
        return lyrics;
    }

    public void setLyrics(InstrumentType lyrics) {
        this.lyrics = lyrics;
    }

}
