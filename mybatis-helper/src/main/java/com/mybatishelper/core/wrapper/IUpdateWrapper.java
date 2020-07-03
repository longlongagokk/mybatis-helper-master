package com.mybatishelper.core.wrapper;

import com.mybatishelper.core.base.meta.ItemPar;

/**
 * @param <S> 自身
 */
public interface IUpdateWrapper<S,C> extends IQueryWrapper<S,C>{
    S set(ItemPar... items);
    S set(String fieldWithAlias, Object value);
}
