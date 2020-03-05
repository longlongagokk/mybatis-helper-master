package club.yourbatis.hi.wrapper.bridge;

import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.consts.ConstValue;
import club.yourbatis.hi.util.TableInfoHelper;
import club.yourbatis.hi.wrapper.IQueryWrapper;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractQueryWrapper<C extends AbstractConditionWrapper,Q extends AbstractQueryWrapper>
        implements IQueryWrapper<Q, C>{
    @Getter
    protected C where;
    protected Map<String, TableMetaInfo> aliases;//check
    protected Map<String, TableMetaInfo> fromTables = new HashMap<>(1<<2);//from
    public AbstractQueryWrapper(C where){
        this(where,new HashMap<>(1<<2));
    }
    public AbstractQueryWrapper(C where,Map<String, TableMetaInfo> aliases){
        this.where = where;
        this.aliases = aliases;
        where.caller = this;
        where.paramAlias = "where.";
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
        TableMetaInfo metaInfo = addMetaInfo(tbClass,ConstValue.MAIN_ALIAS);
        aliases.put(null,metaInfo);
        return (Q)this;
    }
    private TableMetaInfo addMetaInfo(Class<?> tbClass,String alias){
        TableMetaInfo metaInfo = TableInfoHelper.getTableInfoFromEntityClass(tbClass);
        fromTables.put(alias,metaInfo);
        aliases.put(alias,metaInfo);
        return metaInfo;
    }

    protected C copy()throws CloneNotSupportedException{
        return (C)where.clone();
    }
}
