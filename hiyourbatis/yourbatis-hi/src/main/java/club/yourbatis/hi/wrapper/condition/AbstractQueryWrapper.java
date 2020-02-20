package club.yourbatis.hi.wrapper.condition;

import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.wrapper.IConditioner;
import club.yourbatis.hi.wrapper.IQueryWrapper;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractQueryWrapper<L,R,C extends AbstractConditionWrapper<L,R,C>,Q extends AbstractQueryWrapper<L,R,C,Q>>
        implements IQueryWrapper<Q, C>{
    Map<String,IConditioner> conditionWraps = new HashMap<>();
    @Getter
    protected C where;
    protected Map<String, TableMetaInfo> aliases;
    @Getter
    protected TableMetaInfo mainTableMetaInfo;
    public AbstractQueryWrapper(C where){
        this(where,new HashMap<>(1<<2));
    }
    public AbstractQueryWrapper(C where,Map<String, TableMetaInfo> aliases){
        this.where = where;
        this.aliases = aliases;
        where.caller = this;
        where.paramAlias = "where.";
    }
    protected void addAliasTable(String alias, TableMetaInfo tb){
        this.aliases.put(alias,tb);
    }
    protected String getConditionSql() {
        return where.getConditionSql();
    }
    @Override
    public Q where(Consumer<C> consumer) {
        consumer.accept(where);
        return (Q) this;
    }
    protected C copy()throws CloneNotSupportedException{
        return where.clone();
    }
}
