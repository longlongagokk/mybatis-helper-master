package com.mybatishelper.core.cache;

import com.mybatishelper.core.annotation.Column;
import com.mybatishelper.core.annotation.Primary;
import com.mybatishelper.core.annotation.SkipColumn;
import com.mybatishelper.core.annotation.Table;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.util.StringUtility;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public class TableMetaInfo {
    private String tableName;
    /**
     * the primary info
     */
    private EntryFieldInfo primary;
    private Map<String, EntryFieldInfo> fieldInfos;
    public TableMetaInfo(Class<?> entityClass){
        initialization(entityClass);
    }
    private void initialization(Class<?> entityClass){
        Table table = entityClass.getAnnotation(Table.class);
        Assert.notNull(table,"can not load any table info !please check your entity !");
        this.tableName = table.value();
        Class<?> top = entityClass;
        boolean columnToCamelCase = table.columnToCamelCase();
        Map<String,EntryFieldInfo> tmpInfos = new HashMap<>();
        while (top != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            java.lang.reflect.Field[] fields = top.getDeclaredFields();
            for (java.lang.reflect.Field f : fields) {
                //跳过序列：最高级
                SkipColumn skipColumn = f.getAnnotation(SkipColumn.class);
                if(skipColumn != null){
                    continue;
                }
                //默认使用table生成
                String columnName = columnToCamelCase ? StringUtility.toLine(f.getName()) : f.getName();
                columnName = ConstValue.BACK_TRICKS + columnName + ConstValue.BACK_TRICKS;
                //如果有Column：使用Column生成
                Column column = f.getAnnotation(Column.class);
                if(null != column){
                    columnName = column.value();
                }
                Primary primary = f.getAnnotation(Primary.class);
                EntryFieldInfo fieldInfo = EntryFieldInfo.builder()
                        .column(columnName)
                        .field(f)
                        .primaryKey(primary != null)
                        .build();
                tmpInfos.put(f.getName(),fieldInfo);
                if(fieldInfo.isPrimaryKey()){
                    // todo review
                    this.primary = fieldInfo;
                }
            }
            top = top.getSuperclass();
        }
        this.fieldInfos = Collections.unmodifiableMap(tmpInfos);
    }
}
