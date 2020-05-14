package com.mybatishelper.core.wrapper;

/**
 * @param <S> 自身
 */
public interface ICountWrapper<S,C> extends IQueryWrapper<S,C>,
        IJoining<S,C>{
}
