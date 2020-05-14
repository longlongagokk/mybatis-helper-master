package com.mybatishelper.core.wrapper.bridge;

import com.mybatishelper.core.base.meta.TableMetaInfo;
import com.mybatishelper.core.enums.JoinType;
import com.mybatishelper.core.exception.NoSupportedMethodException;
import com.mybatishelper.core.util.TableInfoHelper;
import com.mybatishelper.core.wrapper.IJoining;
import com.mybatishelper.core.wrapper.join.JoinWrapper;
import lombok.Getter;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("WeakerAccess")
public abstract class AbstractJoinerWrapper<C extends AbstractConditionWrapper,Q extends AbstractJoinerWrapper>
        extends AbstractQueryWrapper<C,Q>
        implements IJoining<Q, C> {
    @Getter
    protected List<JoinWrapper> joins;

    public AbstractJoinerWrapper(C where, List<JoinWrapper> joins) {
        super(where);
        this.joins = joins;
    }
    public AbstractJoinerWrapper(C where,AbstractJoinerWrapper absWrapper) {
        super(where);
        if(absWrapper.aliases != null){
            this.aliases = absWrapper.aliases;
            this.fromTables = absWrapper.fromTables;
        }
        this.joins = absWrapper.joins;
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

        this.aliases.put(alias,tableMetaInfo);
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

}
