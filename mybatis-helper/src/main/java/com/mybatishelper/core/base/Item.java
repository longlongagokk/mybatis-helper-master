package com.mybatishelper.core.base;

import com.mybatishelper.core.enums.ItemType;

import java.io.Serializable;

public interface Item<T> extends Serializable {
    T getValue();
    ItemType getType();
}
