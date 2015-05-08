package com.kiluet.jguitar.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChannelParameter", propOrder = {})
@XmlRootElement(name = "channelParameter")
public class ChannelParameter implements Serializable{

    private static final long serialVersionUID = -6282616338933818379L;

    private String key;

    private String value;

    public ChannelParameter() {
        super();
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
