package com.mybatishelper.core.enums;

public enum  ConditionType {
    DO_NOTHING(14," "),
    LEFT_WRAPPER(-3," ( "),
    RIGHT_WRAPPER(-2," ) "),
    CLOSURE(-1,""),
    AND(0," and "),
    OR(1," or "),
    EQ(2," = "),
    GT(3," > "),
    LT(4," < "),
    GE(5," >= "),
    LE(6," <= "),
    NEQ(7," <> "),
    LIKE(8," like "),
    IN(9," in "),
    BETWEEN(10," BETWEEN "),
    NOT_IN(11," not in "),
    ISNULL(12," is null "),
    NOTNULL(13," is not null "),
            ;
    private final int value;
    private final String opera;
    ConditionType(int value, String opera){
        this.value = value;
        this.opera = opera;
    }

    public String getOpera() {
        return opera;
    }

    public int getValue() {
        return value;
    }

}
