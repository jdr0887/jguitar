package com.kiluet.jguitar.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Chord", propOrder = {})
@XmlRootElement(name = "chord")
public class Chord implements Serializable {

    private static final long serialVersionUID = 285673711759665158L;

    private Integer firstFret;

    private List<Integer> strings;

    private String name;

    private Beat beat;

    public Chord() {
    }

    public Integer getFirstFret() {
        return firstFret;
    }

    public void setFirstFret(Integer firstFret) {
        this.firstFret = firstFret;
    }

    public List<Integer> getStrings() {
        return strings;
    }

    public void setStrings(List<Integer> strings) {
        this.strings = strings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Beat getBeat() {
        return beat;
    }

    public void setBeat(Beat beat) {
        this.beat = beat;
    }

}
