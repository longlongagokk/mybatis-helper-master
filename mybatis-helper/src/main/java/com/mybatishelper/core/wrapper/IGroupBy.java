package com.mybatishelper.core.wrapper;

import com.mybatishelper.core.base.meta.GroupInfo;

public interface IGroupBy<S> {
    S groupBy(String fields);
    S groupBy(GroupInfo...items);
}
