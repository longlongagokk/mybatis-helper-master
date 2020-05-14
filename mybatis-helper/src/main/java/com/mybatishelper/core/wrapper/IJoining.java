package com.mybatishelper.core.wrapper;

import java.util.function.Consumer;

public interface IJoining<S,C> {
    S join(Class<?> tbClass,String alias, Consumer<C> consumer);
    S leftJoin(Class<?> tbClass,String alias, Consumer<C> consumer);
    S rightJoin(Class<?> tbClass,String alias, Consumer<C> consumer);
    S innerJoin(Class<?> tbClass,String alias, Consumer<C> consumer);
    S outerJoin(Class<?> tbClass,String alias, Consumer<C> consumer);
}
