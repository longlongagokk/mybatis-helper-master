package com.mybatishelper.core.wrapper.bridge;

import com.mybatishelper.core.cache.TableMetaInfo;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.enums.JoinType;
import com.mybatishelper.core.exception.NoSupportedMethodException;
import com.mybatishelper.core.util.TableInfoHelper;
import com.mybatishelper.core.wrapper.IQueryWrapper;
import com.mybatishelper.core.wrapper.factory.PropertyConditionWrapper;
import com.mybatishelper.core.wrapper.join.JoinWrapper;
import com.mybatishelper.core.wrapper.query.SelectWrapper;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractQueryWrapper<C extends AbstractConditionWrapper,Q extends AbstractQueryWrapper>
        implements IQueryWrapper<Q, C> {
    @Getter
    protected List<JoinWrapper> joins;
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

    public AbstractQueryWrapper(C where, List<JoinWrapper> joins) {
        this(where);
        this.joins = joins;
    }
    public AbstractQueryWrapper(C where,AbstractQueryWrapper absWrapper) {
        this(where);
        if(absWrapper.aliasTables != null){
            this.aliasTables = absWrapper.aliasTables;
            this.fromTables = absWrapper.fromTables;
        }
        this.joins = absWrapper.joins;
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

    @Override
    public Q join(Class<?> tbClass,String alias, Consumer<C> consumer) {
        return join(tbClass,alias, consumer, JoinType.JOIN);
    }

    @Override
    public Q leftJoin(Class<?> tbClass,String alias, Consumer<C> consumer) {
        return join(tbClass,alias, consumer, JoinType.LEFT_JOIN);
    }

    @Override
    public Q rightJoin(Class<?> tbClass,String alias, Consumer<C> consumer) {
        return join(tbClass,alias, consumer, JoinType.RIGHT_JOIN);
    }

    @Override
    public Q innerJoin(Class<?> tbClass,String alias, Consumer<C> consumer) {
        return join(tbClass,alias, consumer, JoinType.INNER_JOIN);
    }

    @Override
    public Q outerJoin(Class<?> tbClass,String alias, Consumer<C> consumer) {
        return join(tbClass,alias, consumer, JoinType.OUTER_JOIN);
    }

    private Q join(Class<?> tbClass,String alias, Consumer<C> consumer, JoinType joinType) {
        TableMetaInfo tableMetaInfo = TableInfoHelper.getTableInfoFromEntityClass(tbClass);

        this.aliasTables.put(alias,tableMetaInfo);
        try {
            C joinWhere = (C)where.clone();
            joinWhere.reset(AbstractConditionWrapper.DEFAULT_CONDITION_ELEMENTS_SIZE,this,"joins[" + joins.size() + "].where.");
            consumer.accept(joinWhere);
            //
            joins.add(new JoinWrapper(joinType, tableMetaInfo,alias, joinWhere));
        }catch (Exception e){
            throw new NoSupportedMethodException("condition impl no has clone method !");
        }

        return (Q) this;
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
