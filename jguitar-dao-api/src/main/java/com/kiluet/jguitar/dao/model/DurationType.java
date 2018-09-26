package com.kiluet.jguitar.dao.model;

public enum DurationType {

    WHOLE(1, 64),

    HALF(2, 32),

    QUARTER(4, 16),

    EIGHTH(8, 8),

    SIXTEENTH(16, 4),

    THIRTY_SECOND(32, 2),

    SIXTY_FOURTH(64, 1);

    private Integer code;

    private Integer length;

    private DurationType(Integer code, Integer length) {
        this.code = code;
        this.length = length;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

}
