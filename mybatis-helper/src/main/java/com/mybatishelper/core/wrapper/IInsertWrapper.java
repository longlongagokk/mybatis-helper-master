package com.mybatishelper.core.wrapper;

import com.mybatishelper.core.base.meta.InsertInfo;

/**
 * @param <S> 自身
 */
public interface IInsertWrapper<S,C> extends IQueryWrapper<S,C>{
    S insertInto(Class<?> tbClass);
    S select(InsertInfo...insertInfos);
    S fieldWithValue(String fieldWithAlias, Object value);
}
