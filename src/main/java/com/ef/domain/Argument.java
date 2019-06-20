package com.ef.domain;

public class Argument {

    private ArgumentEnum key;
    private String value;

    public Argument(ArgumentEnum key, String value) {
        this.key = key;
        this.value = value;
    }

    public ArgumentEnum getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
