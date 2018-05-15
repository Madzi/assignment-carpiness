package com.carpiness.job.input.token;

public class NumberToken implements Token {

    private double value;

    public NumberToken(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

}
