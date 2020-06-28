package com.mybatishelper.core.wrapper.insert;

import com.mybatishelper.core.cache.EntryFieldInfo;
import com.mybatishelper.core.cache.TableMetaInfo;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.util.TableInfoHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 后期记得加缓存
 */
@Slf4j
public class InsertSqlProvider {
    public String insert(Object entity)throws Exception{
        return _insert(entity,false);
    }

    public String insertSelective(Object entity)throws Exception{
        return _insert(entity,true);
    }

    private String _insert(Object entity,boolean skipNull)throws Exception{
        Assert.notNull(entity,"entity could not be null!");
        TableMetaInfo tableMetaInfo = TableInfoHelper.getTableInfoFromEntityClass(entity.getClass());
        StringBuilder insertSql = new StringBuilder("insert into ").append(tableMetaInfo.getTableName()).append("(");
        StringBuilder fieldSql = new StringBuilder();
        StringBuilder valueSql = new StringBuilder();
        for(Map.Entry<String, EntryFieldInfo> entry : tableMetaInfo.getFieldInfos().entrySet()){
            EntryFieldInfo fieldInfo = entry.getValue();
            fieldInfo.getField().setAccessible(true);
            if (skipNull && fieldInfo.getField().get(entity) == null) {
                continue;
            }
            fieldSql.append(fieldInfo.getColumn()).append(",");
            valueSql.append("#{").append(entry.getKey()).append("},");
        }
        if(fieldSql.length() == 0){
            throw new IllegalArgumentException("no field to insert ！");
        }
        fieldSql.deleteCharAt(fieldSql.length() - 1);
        valueSql.deleteCharAt(valueSql.length() - 1);
        return insertSql.append(fieldSql).append(") VALUES (").append(valueSql).append(")").toString();
    }

}
