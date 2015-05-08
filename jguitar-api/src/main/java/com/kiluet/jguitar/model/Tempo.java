package com.kiluet.jguitar.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tempo", propOrder = {})
@XmlRootElement(name = "tempo")
public class Tempo implements Serializable {

    private static final long serialVersionUID = 400834507062684110L;

    private Integer value = 120;

    public Tempo() {
        this.value = 120;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
