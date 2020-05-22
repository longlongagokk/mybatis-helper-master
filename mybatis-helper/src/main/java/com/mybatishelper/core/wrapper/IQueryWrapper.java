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
    S where(Consumer<C> consumer);
}
