package com.mybatishelper.core.wrapper.bridge;

import com.mybatishelper.core.cache.TableMetaInfo;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.util.CollectionUtils;
import com.mybatishelper.core.util.StringUtils;
import com.mybatishelper.core.util.TableInfoHelper;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Map;

public abstract class AbsSqlProvider {

    public static final String insert = "insert";
    public static final String insertSelective = "insertSelective";
    public static final String insertSelectItem = "insertSelectItem";
    public static final String batchInsert = "batchInsert";

    public static final String deleteByPrimaryKey = "deleteByPrimaryKey";
    public static final String delete = "delete";

    public static final String selectItemByPrimaryKey = "selectItemByPrimaryKey";
    public static final String selectOne = "selectOne";
    public static final String selectList = "selectList";
    public static final String selectPageList = "selectPageList";
    public static final String selectCount = "selectCount";

    public static final String updateByPrimary = "updateByPrimary";
    public static final String updateSelectiveByPrimaryKey = "updateSelectiveByPrimaryKey";
    public static final String updateSelectItem = "updateSelectItem";
    public static final String selectExists = "selectExists";

    protected Map<String, TableMetaInfo> checkAndReturnFromTables(ProviderContext context, AbstractQueryWrapper<?,?> wrapper){
        if(wrapper.fromTables.isEmpty()){
            TableMetaInfo mainMeta = TableInfoHelper.getTableInfoByProviderContext(context);
            wrapper.aliasTables.put(ConstValue.MAIN_ALIAS,mainMeta);
            wrapper.fromTables.put(ConstValue.MAIN_ALIAS,mainMeta);
        }
        return wrapper.fromTables;
    }
    protected void createFromTableSql(StringBuilder builder,AbstractQueryWrapper<?,?> wrapper){
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
    protected void createJoinInfoSql(StringBuilder builder,AbstractQueryWrapper<?,?> wrapper){
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
     * @param builder sqlContainer
     * @param wrapper query container
     * @return hasCondition when conditionSql is empty then return null else true
     */
    protected boolean createWhereSql(StringBuilder builder,AbstractQueryWrapper<?,?> wrapper){
        String conditionSql = wrapper.where.getConditionSql();
        if(!StringUtils.isEmpty(conditionSql)){
            builder.append(" where ").append(conditionSql);
            return true;
        }
        return false;
    }
}
