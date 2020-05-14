package com.mybatishelper.core.wrapper;

public interface IDeleteWrapper<S,C> extends IQueryWrapper<S,C>{
    S delete(String alias);
}
