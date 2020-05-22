package com.mybatishelper.core.wrapper.bridge;

import com.mybatishelper.core.base.meta.TableMetaInfo;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.util.TableInfoHelper;
import com.mybatishelper.core.wrapper.IQueryWrapper;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractQueryWrapper<C extends AbstractConditionWrapper,Q extends AbstractQueryWrapper>
        implements IQueryWrapper<Q, C> {
    @Getter
    protected C where;
    protected Map<String, TableMetaInfo> aliasTables;// check
    protected Map<String, TableMetaInfo> fromTables = new HashMap<>(1<<2);//from
    public AbstractQueryWrapper(C where){
        this(where,new HashMap<>(1<<2));
    }
    public AbstractQueryWrapper(C where,Map<String, TableMetaInfo> aliasTables){
        this.where = where;
        this.aliasTables = aliasTables;
        where.caller = this;
        where.paramAlias = "where.";
    }

    @Override
    public Set<String> getAliases() {
        return fromTables.keySet();
    }

    @Override
    public Q where(Consumer<C> consumer) {
        consumer.accept(where);
        return (Q) this;
    }

    @Override
    public Q from(Class<?> tbClass, String alias) {
        addMetaInfo(tbClass,alias);
        return (Q)this;
    }

    @Override
    public Q from(Class<?> tbClass) {
        addMetaInfo(tbClass,ConstValue.MAIN_ALIAS);
        return (Q)this;
    }
    private TableMetaInfo addMetaInfo(Class<?> tbClass,String alias){
        TableMetaInfo metaInfo = TableInfoHelper.getTableInfoFromEntityClass(tbClass);
        fromTables.put(alias,metaInfo);
        aliasTables.put(alias,metaInfo);
        return metaInfo;
    }

    protected C copy()throws CloneNotSupportedException{
        return (C)where.clone();
    }
}
