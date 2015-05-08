package com.kiluet.jguitar.model;

public enum InstrumentType {

    GUITAR(6),

    BASS(4),

    BANJO(6);

    private Integer stringCount;

    private InstrumentType(Integer stringCount) {
        this.stringCount = stringCount;
    }

    public Integer getStringCount() {
        return stringCount;
    }

    public void setStringCount(Integer stringCount) {
        this.stringCount = stringCount;
    }

}
