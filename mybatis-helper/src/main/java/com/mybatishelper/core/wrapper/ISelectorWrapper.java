package com.mybatishelper.core.wrapper;

import com.mybatishelper.core.base.meta.SelectInfo;

import java.util.Collection;

/**
 * @param <S> 自身
 */
public interface ISelectorWrapper<S,C>
        extends IQueryWrapper<S,C>,
        IPager<S>, IOrder<S>
{
    Collection<SelectInfo> selects();
    S select(SelectInfo... fields);
    S select(String fields);
    S selectMain(boolean selectMain);
    S lock(boolean lock);
    <N extends S> N back();
}
