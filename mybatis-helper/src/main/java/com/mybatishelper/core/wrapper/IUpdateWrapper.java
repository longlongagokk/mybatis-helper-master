package com.mybatishelper.core.wrapper;

import com.mybatishelper.core.base.meta.UpdateInfo;

/**
 * @param <S> 自身
 */
public interface IUpdateWrapper<S,C> extends IQueryWrapper<S,C>{
    S set(UpdateInfo... items);
    S set(String fieldWithAlias, Object value);
}
