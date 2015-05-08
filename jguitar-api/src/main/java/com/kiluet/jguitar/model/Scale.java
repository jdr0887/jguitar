package com.kiluet.jguitar.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Scale", propOrder = {})
@XmlRootElement(name = "scale")
public class Scale implements Serializable {

    private static final long serialVersionUID = 7403269170535468749L;

    private List<Boolean> notes;

    private Integer key;

    public Scale() {
        super();
    }

    public List<Boolean> getNotes() {
        return notes;
    }

    public void setNotes(List<Boolean> notes) {
        this.notes = notes;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

}
