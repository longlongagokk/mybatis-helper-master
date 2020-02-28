package club.yourbatis.hi.wrapper.bridge;

import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.consts.ConstValue;
import club.yourbatis.hi.util.CollectionUtils;
import club.yourbatis.hi.util.TableInfoHelper;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.util.StringUtils;

import java.util.Map;

public abstract class AbsSqlProvider {
    protected Map<String, TableMetaInfo> checkAndReturnFromTables(ProviderContext context, AbstractJoinerWrapper<?,?> wrapper){
        if(wrapper.fromTables.isEmpty()){
            TableMetaInfo mainMeta = TableInfoHelper.getTableInfoByProviderContext(context);
            wrapper.aliases.put(null,mainMeta);
            wrapper.aliases.put(ConstValue.MAIN_ALIAS,mainMeta);
            wrapper.fromTables.put(ConstValue.MAIN_ALIAS,mainMeta);
        }
        return wrapper.fromTables;
    }
    protected void createFromTableSql(StringBuilder builder,AbstractJoinerWrapper<?,?> wrapper){
        for(Map.Entry<String,TableMetaInfo> entry:wrapper.fromTables.entrySet()){
            builder
                    .append(entry.getValue().getTableName())
                    .append(ConstValue.BLANK)
                    .append(entry.getKey())
                    .append(ConstValue.COMMA)
            ;
        }
        builder.deleteCharAt(builder.length() - 1).append(ConstValue.BLANK);
    }
    protected void createJoinInfoSql(StringBuilder builder,AbstractJoinerWrapper<?,?> wrapper){
        if(!CollectionUtils.isEmpty(wrapper.joins)){
            builder.append(ConstValue.BLANK);
            wrapper.joins.forEach(x -> {
                builder
                        .append(x.getJoinType().getValue())// join
                        .append(x.getTableInfo().getTableName())//
                        .append(ConstValue.BLANK)//
                        .append(x.getAlias())//
                        .append(ConstValue.BLANK)//
                        .append(ConstValue.ON)// on
                        .append(x.getWhere().getConditionSql());// where
            });
        }
    }

    /**
     * append where sql and return has condition
     * @param builder
     * @param wrapper
     * @return
     */
    protected boolean createWhereSql(StringBuilder builder,AbstractJoinerWrapper<?,?> wrapper){
        String conditionSql = wrapper.where.getConditionSql();
        if(!StringUtils.isEmpty(conditionSql)){
            builder.append(" where ").append(conditionSql);
            return true;
        }
        return false;
    }
}
