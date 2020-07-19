package com.mybatishelper.core.wrapper.insert;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.meta.InsertInfo;
import com.mybatishelper.core.base.meta.UpdateInfo;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.base.param.ValueItem;
import com.mybatishelper.core.cache.EntryFieldInfo;
import com.mybatishelper.core.cache.TableMetaInfo;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.enums.ItemType;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.util.TableInfoHelper;
import com.mybatishelper.core.wrapper.IInsertWrapper;
import com.mybatishelper.core.wrapper.IUpdateWrapper;
import com.mybatishelper.core.wrapper.bridge.AbsSqlProvider;
import com.mybatishelper.core.wrapper.seg.SimpleConditionSeg;
import com.mybatishelper.core.wrapper.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 后期记得加缓存
 */
@Slf4j
public class InsertSqlProvider extends AbsSqlProvider {
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


    public String insertSelectItem(ProviderContext context, IInsertWrapper wrapper) {
        InsertWrapper insertWrapper = (InsertWrapper)wrapper;
        Assert.notNull(insertWrapper.insertTable,"insert table can not be null !");
        Assert.notEmpty(insertWrapper.insertInfos,"insert elements can not be empty !");
        insertWrapper.insertItems = new ArrayList(insertWrapper.insertInfos);
        //已经是非重复且按顺序的了
        List<InsertInfo> fvs = insertWrapper.insertItems;

        checkAndReturnFromTables(context,insertWrapper);

        StringBuilder insertSql = new StringBuilder("insert into ");

        //into tables
        insertSql.append(insertWrapper.insertTable.getTableName());


        //wrap columns and values;
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        Map<String,EntryFieldInfo> fieldInfos = insertWrapper.insertTable.getFieldInfos();
        for(int i = 0; i < fvs.size(); ++i){
            InsertInfo info = fvs.get(i);
            Item key = info.getKey();
            String column;
            if(key.getType() == ItemType.VALUE){
                //ori column
                column = (String)key.getValue();
            }else{
                EntryFieldInfo fieldInfo = fieldInfos.get(((FieldItem)key).getName());
                column = fieldInfo.getColumn();
            }
            columns.append(column).append(ConstValue.COMMA);

            Item value = info.getValue();
            if(value.getType() == ItemType.PARAM){
                value = ValueItem.valueOf("#{insertItems["+i+"].value.value}");
            }
            values.append(SimpleConditionSeg.valueOf(ConditionType.DO_NOTHING,value).createSql(insertWrapper)).append(ConstValue.COMMA);
        }
        columns.deleteCharAt(columns.length() - 1);
        values.deleteCharAt(values.length() - 1);

        //insert columns
        insertSql.append("(");
        insertSql.append(columns);
        insertSql.append(")");

        //select values
        insertSql.append("select ");
        insertSql.append(values);
        insertSql.append(ConstValue.BLANK);

        //from tables
        if(insertWrapper.fromTableSize() > 0) {
            createFromTableSql(insertSql, insertWrapper);
        }else{
            insertSql.append(" from dual ");
        }
        //join infos
        createJoinInfoSql(insertSql,insertWrapper);

        //conditions
        createWhereSql(insertSql,insertWrapper);
        return insertSql.toString();
    }

}
