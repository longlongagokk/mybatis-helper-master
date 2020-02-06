package com.vitily.common.util;

import club.yourbatis.hi.annotation.Table;
import club.yourbatis.hi.base.TableInfo;
import club.yourbatis.hi.consts.ConstValue;
import org.springframework.util.StringUtils;

public class ClassAssociateTableInfo implements TableInfo {
    private String tableName;
    private String alias;
    private Class<?> tableClass;
    private ClassAssociateTableInfo(Class<?> clazz,String alias){
        Table table = clazz.getAnnotation(Table.class);
        this.tableName = table.value();
        this.alias = alias;
        this.tableClass = clazz;
    }
    public static final ClassAssociateTableInfo valueOf(Class<?> clazz){
        return valueOf(clazz,null);
    }
    public static final ClassAssociateTableInfo valueOf(Class<?> clazz,String alias){
        return new ClassAssociateTableInfo(clazz,alias);
    }
    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getTableNameWithAlias() {
        if(StringUtils.isEmpty(alias)){
            return getTableName();
        }
        return getTableName() + ConstValue.BLANK + getAlias();
    }

    @Override
    public Class<?> getTableClass() {
        return tableClass;
    }
}
