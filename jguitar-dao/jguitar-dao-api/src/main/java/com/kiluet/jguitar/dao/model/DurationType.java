package com.kiluet.jguitar.dao.model;

public enum DurationType {

    WHOLE(1),

    HALF(2),

    QUARTER(4),

    EIGHTH(8),

    SIXTEENTH(16),

    THIRTY_SECOND(32),

    SIXTY_FOURTH(64);

    private Integer code;

    private DurationType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
