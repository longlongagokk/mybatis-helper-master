package com.mybatishelper.core.base;

import com.mybatishelper.core.base.param.FieldItem;

public interface FieldValue<T> {
    FieldItem getLeft();
    Item<T> getRight();
    T getValue();
}
