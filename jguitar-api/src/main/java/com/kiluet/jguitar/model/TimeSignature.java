package com.kiluet.jguitar.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeSignature", propOrder = {})
@XmlRootElement(name = "timeSignature")
public class TimeSignature implements Serializable {

    private static final long serialVersionUID = -1572081477783987413L;

    private Integer numerator;

    public TimeSignature() {
        super();
    }

    public Integer getNumerator() {
        return numerator;
    }

    public void setNumerator(Integer numerator) {
        this.numerator = numerator;
    }

}
