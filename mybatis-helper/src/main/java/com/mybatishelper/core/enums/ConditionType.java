package com.mybatishelper.core.enums;

public enum  ConditionType {
    DO_NOTHING(14," "),
    LEFT_WRAPPER(-3," ( "),
    RIGHT_WRAPPER(-2," ) "),
    CLOSURE(-1,""),
    AND(0," AND "),
    OR(1," OR "),
    EQ(2," = "),
    GT(3," > "),
    LT(4," < "),
    GE(5," >= "),
    LE(6," <= "),
    NEQ(7," <> "),
    LIKE(8," LIKE "),
    IN(9," IN "),
    BETWEEN(10," BETWEEN "),
    NOT_IN(11," NOT IN "),
    ISNULL(12," IS NULL "),
    NOTNULL(13," IS NOT NULL "),
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
