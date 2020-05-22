package com.mybatishelper.core.base;

public interface Field {
    boolean isOriginal();
    String getAlias();
    String getName();
    String getFullName();
    String getFullPath();
}