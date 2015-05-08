package com.kiluet.jguitar.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Note", propOrder = {})
@XmlRootElement(name = "note")
public class Note implements Serializable {

    private static final long serialVersionUID = 3627594670573036614L;

    @XmlAttribute
    private Integer string;

    @XmlAttribute
    private Integer value;

    @XmlAttribute
    private Integer velocity;

    public Note() {
        super();
    }

    public Note(Integer string, Integer value) {
        super();
        this.string = string;
        this.value = value;
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

    public Integer getString() {
        return string;
    }

    public void setString(Integer string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return String.format("Note [value=%s, velocity=%s, string=%s]", value, velocity, string);
    }

}