package com.mybatishelper.core.wrapper;

import com.mybatishelper.core.base.field.OrderField;

public interface IOrder<S> {
    S orderBy(OrderField... fields);
    S orderBy(String strings);
}
