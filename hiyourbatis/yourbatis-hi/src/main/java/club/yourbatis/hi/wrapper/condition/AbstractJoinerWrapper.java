package club.yourbatis.hi.wrapper.condition;

import club.yourbatis.hi.base.TableInfo;
import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.consts.ConstValue;
import club.yourbatis.hi.enums.JoinType;
import club.yourbatis.hi.exception.NoSupportedMethodException;
import club.yourbatis.hi.util.TableInfoHelper;
import club.yourbatis.hi.wrapper.IJoining;
import club.yourbatis.hi.wrapper.IQueryWrapper;
import club.yourbatis.hi.wrapper.join.JoinWrapper;
import lombok.Getter;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("WeakerAccess")
public abstract class AbstractJoinerWrapper<L,R,C extends AbstractConditionWrapper<L,R,C>,Q extends AbstractJoinerWrapper<L,R,C,Q>>
        extends AbstractQueryWrapper<L,R,C,Q>
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
        }
        this.joins = absWrapper.joins;
    }
    protected void addAliasTable(String alias, TableMetaInfo tb) {
        super.addAliasTable(alias,tb);
    }

    protected String getConditionSql() {
        return where.getConditionSql();
    }

    protected String getJoinerSql() {
        final StringBuilder joinStr = new StringBuilder();
        joins.forEach(x -> {
            joinStr
                    .append(x.getJoinType().getValue())// join
                    .append(x.getTableInfo().getTableNameWithAlias())// tablex aliasx
                    .append(ConstValue.ON)// on
                    .append(x.getWhere().getConditionSql());// where
        });
        return joinStr.toString();
    }


    @Override
    public Q join(TableInfo tableInfo, Consumer<C> consumer) {
        return join(tableInfo, consumer, JoinType.JOIN);
    }

    @Override
    public Q leftJoin(TableInfo tableInfo, Consumer<C> consumer) {
        return join(tableInfo, consumer, JoinType.LEFT_JOIN);
    }

    @Override
    public Q rightJoin(TableInfo tableInfo, Consumer<C> consumer) {
        return join(tableInfo, consumer, JoinType.RIGHT_JOIN);
    }

    @Override
    public Q innerJoin(TableInfo tableInfo, Consumer<C> consumer) {
        return join(tableInfo, consumer, JoinType.INNER_JOIN);
    }

    @Override
    public Q outerJoin(TableInfo tableInfo, Consumer<C> consumer) {
        return join(tableInfo, consumer, JoinType.OUTER_JOIN);
    }

    private Q join(TableInfo tableInfo, Consumer<C> consumer, JoinType joinType) {
        TableMetaInfo tableMetaInfo = TableInfoHelper.getTableInfoFromEntityClass(tableInfo.getTableClass());
        addAliasTable(tableInfo.getAlias(),tableMetaInfo);
        try {
            C joinWhere = where.clone();
            joinWhere.reset(AbstractConditionWrapper.DEFAULT_CONDITION_ELEMENTS_SIZE,this,"joins[" + joins.size() + "].where.");
            consumer.accept(joinWhere);
            //
            joins.add(new JoinWrapper(joinType, tableInfo, joinWhere));
        }catch (Exception e){
            throw new NoSupportedMethodException("condition impl no has clone method !");
        }

        return (Q) this;
    }

}
