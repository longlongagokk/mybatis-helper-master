package com.mybatishelper.core.wrapper;

import com.mybatishelper.core.base.FieldValue;

/**
 * @param <S> 自身
 */
public interface IUpdateWrapper<S,C> extends IQueryWrapper<S,C>{
    S set(FieldValue... items);
    S set(String fieldWithAlias, Object value);
}
