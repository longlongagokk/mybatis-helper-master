package com.mybatishelper.core.base;

import com.mybatishelper.core.enums.ItemType;

public interface Item<T> {
    T getValue();
    Item<T> withValue(T value);
    ItemType getType();
}
