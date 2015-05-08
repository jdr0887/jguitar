package com.kiluet.jguitar.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Channel", propOrder = {})
@XmlRootElement(name = "channel")
public class Channel implements Serializable{

    private static final long serialVersionUID = 1486129760257893815L;

    private int channelId;

    private short bank;

    private short program;

    private short volume;

    private short balance;

    private short chorus;

    private short reverb;

    private short phaser;

    private short tremolo;

    private String name;

    private List<ChannelParameter> parameters;

    public Channel() {
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public short getBank() {
        return bank;
    }

    public void setBank(short bank) {
        this.bank = bank;
    }

    public short getProgram() {
        return program;
    }

    public void setProgram(short program) {
        this.program = program;
    }

    public short getVolume() {
        return volume;
    }

    public void setVolume(short volume) {
        this.volume = volume;
    }

    public short getBalance() {
        return balance;
    }

    public void setBalance(short balance) {
        this.balance = balance;
    }

    public short getChorus() {
        return chorus;
    }

    public void setChorus(short chorus) {
        this.chorus = chorus;
    }

    public short getReverb() {
        return reverb;
    }

    public void setReverb(short reverb) {
        this.reverb = reverb;
    }

    public short getPhaser() {
        return phaser;
    }

    public void setPhaser(short phaser) {
        this.phaser = phaser;
    }

    public short getTremolo() {
        return tremolo;
    }

    public void setTremolo(short tremolo) {
        this.tremolo = tremolo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChannelParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<ChannelParameter> parameters) {
        this.parameters = parameters;
    }

}
