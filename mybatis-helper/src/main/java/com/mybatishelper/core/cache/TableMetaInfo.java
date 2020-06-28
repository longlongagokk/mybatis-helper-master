package com.mybatishelper.core.cache;

import com.mybatishelper.core.annotation.Column;
import com.mybatishelper.core.annotation.Table;
import com.mybatishelper.core.util.Assert;
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
        this.tableName = entityClass.getAnnotation(Table.class).value();
        Assert.notNull(this.tableName,"can not load any table info !please check your entity !");
        Class<?> top = entityClass;
        Map<String,EntryFieldInfo> tmpInfos = new HashMap<>();
        while (top != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            java.lang.reflect.Field[] fields = top.getDeclaredFields();
            for (java.lang.reflect.Field f : fields) {
                Column column = f.getAnnotation(Column.class);
                if(null == column){
                    continue;
                }
                EntryFieldInfo fieldInfo = EntryFieldInfo.builder()
                        .column(column.value())
                        .field(f)
                        .primaryKey(column.primaryKey())
                        .build();
                tmpInfos.put(f.getName(),fieldInfo);
                if(column.primaryKey()){
                    // todo review
                    this.primary = fieldInfo;
                }
            }
            top = top.getSuperclass();
        }
        this.fieldInfos = Collections.unmodifiableMap(tmpInfos);
    }
}
