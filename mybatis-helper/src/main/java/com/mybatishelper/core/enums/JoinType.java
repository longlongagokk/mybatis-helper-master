package com.mybatishelper.core.enums;

public enum JoinType {
    JOIN(" JOIN "),
    LEFT_JOIN(" LEFT OUTER JOIN "),
    RIGHT_JOIN(" RIGHT OUTER JOIN "),
    INNER_JOIN(" INNER JOIN ")
    ;
    private String value;
    JoinType(String value) {this.value = value;}

    public String getValue() {
        return value;
    }
}
