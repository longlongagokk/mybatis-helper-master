package club.yourbatis.hi.wrapper;

import club.yourbatis.hi.base.TableInfo;

import java.util.function.Consumer;

public interface IJoining<S,C> {
    S join(TableInfo tableInfo, Consumer<C> consumer);
    S leftJoin(TableInfo tableInfo, Consumer<C> consumer);
    S rightJoin(TableInfo tableInfo, Consumer<C> consumer);
    S innerJoin(TableInfo tableInfo, Consumer<C> consumer);
    S outerJoin(TableInfo tableInfo, Consumer<C> consumer);
}
