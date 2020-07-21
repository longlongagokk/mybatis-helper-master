package com.mybatishelper.core.wrapper;

public interface IHaving<S> {
    S having(String originalSql,Object...params);
}
