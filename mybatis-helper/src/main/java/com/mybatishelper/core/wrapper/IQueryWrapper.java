package com.mybatishelper.core.wrapper;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @param <S> 自身
 * @param <C> 查询条件
 */
public interface IQueryWrapper<S,C> extends IWrapper {
    C getWhere();
    Set<String> getAliases();
    S from(Class<?> tbClass,String alias);
    S from(Class<?> tbClass);
    S join(Class<?> tbClass,String alias, Consumer<C> consumer);
    S leftJoin(Class<?> tbClass,String alias, Consumer<C> consumer);
    S rightJoin(Class<?> tbClass,String alias, Consumer<C> consumer);
    S innerJoin(Class<?> tbClass,String alias, Consumer<C> consumer);
    S outerJoin(Class<?> tbClass,String alias, Consumer<C> consumer);
    S where(Consumer<C> consumer);
}
