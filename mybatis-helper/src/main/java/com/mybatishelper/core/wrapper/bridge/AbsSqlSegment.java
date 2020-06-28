package com.mybatishelper.core.wrapper.bridge;

import com.mybatishelper.core.base.Field;
import com.mybatishelper.core.cache.EntryFieldInfo;
import com.mybatishelper.core.cache.TableMetaInfo;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.util.StringUtils;
import com.mybatishelper.core.wrapper.ISqlSegment;

public abstract class AbsSqlSegment<T> implements ISqlSegment {
    protected ConditionType type;
    protected T[] items;

    @SafeVarargs
    protected AbsSqlSegment(ConditionType type, T... items) {
        this.type = type;
        this.items = items;
    }

    protected String wrapSql(Field oriField, AbstractQueryWrapper<?,?> wrapper){
        if(oriField.isOriginal()){
            return oriField.getFullPath();
        }
        String tbAlias = oriField.getAlias();
        if(StringUtils.isEmpty(tbAlias)){
            tbAlias = ConstValue.MAIN_ALIAS;
        }
        TableMetaInfo tb = wrapper.aliasTables.get(tbAlias);
        //if tb is null then some alias not in sql,please check sql's alias
        EntryFieldInfo fieldInfo = tb.getFieldInfos().get(oriField.getName());
        Assert.notNull(fieldInfo,"column can't find in " + tb.getTableName() + " with field [" + oriField.getFullName()+"]");
        return wrapField(oriField,fieldInfo.getColumn()).getFullPath();
    }

    protected abstract Field wrapField(Field oriField,String column);
}
