package com.kiluet.jguitar.dao.model;

public enum MeterType {

    COMMON(4, 4),

    WALTZ(3, 4),

    MARCH(2, 4),

    SIX_EIGHT(6, 8);

    private Integer numerator;

    private Integer denominator;

    private MeterType(Integer numerator, Integer denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Integer getNumerator() {
        return numerator;
    }

    public void setNumerator(Integer numerator) {
        this.numerator = numerator;
    }

    public Integer getDenominator() {
        return denominator;
    }

    public void setDenominator(Integer denominator) {
        this.denominator = denominator;
    }

}
