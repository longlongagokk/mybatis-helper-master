package com.mybatishelper.core.wrapper;

import com.mybatishelper.core.base.meta.SortInfo;

public interface IOrder<S> {
    S orderBy(SortInfo... items);
    S orderBy(String strings);
}
