package com.mybatishelper.core.wrapper;

import com.mybatishelper.core.base.Page;

public interface IPager<S> {
    Page getPage();
    S page(Page page);
}
