package com.mybatishelper.core.wrapper.delete;

import com.mybatishelper.core.base.Primary;
import com.mybatishelper.core.cache.TableMetaInfo;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.util.TableInfoHelper;
import com.mybatishelper.core.wrapper.IDeleteWrapper;
import com.mybatishelper.core.wrapper.bridge.AbsSqlProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Map;

@Slf4j
public class DeleteSqlProvider extends AbsSqlProvider {

    public String deleteByPrimaryKey(ProviderContext context, Primary primary) {
        TableMetaInfo tableMetaInfo = TableInfoHelper.getTableInfoByProviderContext(context);
        Assert.notNull(tableMetaInfo.getPrimary(),String.format("table {0} do not have any primaryKey!", tableMetaInfo.getTableName()));
        return new StringBuilder("delete from ")
                .append(tableMetaInfo.getTableName())
                .append(" where ")
                .append(tableMetaInfo.getPrimary().getColumn())
                .append(" = #{value}")
                .toString();
    }

    public String delete(ProviderContext context, IDeleteWrapper wrapper) {
        DeleteWrapper deleteWrapper = (DeleteWrapper)wrapper;
        Map<String, TableMetaInfo> tableMetaInfoMap = checkAndReturnFromTables(context,deleteWrapper);
        StringBuilder deleteSql = new StringBuilder("delete ");

        //delete alias
        deleteWrapper.deleteAlias.forEach(x->
                {
                    if(tableMetaInfoMap.containsKey(x)) {
                        deleteSql.append(x).append(ConstValue.COMMA);
                    }
                }
                );
        if(deleteWrapper.deleteAlias.isEmpty()){
            deleteSql.append(ConstValue.MAIN_ALIAS);
        }else{
            deleteSql.deleteCharAt(deleteSql.length() - 1);
        }

        deleteSql.append(" from ");

        //from tables
        createFromTableSql(deleteSql,deleteWrapper);

        //join infos
        createJoinInfoSql(deleteSql,deleteWrapper);

        //conditions
        if(!createWhereSql(deleteSql,deleteWrapper)){
            throw new IllegalArgumentException("could not update table with no conditions!");
        }

        return deleteSql.toString();
    }
}
