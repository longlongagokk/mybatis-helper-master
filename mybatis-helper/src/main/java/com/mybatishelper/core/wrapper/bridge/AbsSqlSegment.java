package com.mybatishelper.core.wrapper.bridge;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.cache.EntryFieldInfo;
import com.mybatishelper.core.cache.TableMetaInfo;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.enums.ItemType;
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

    /**
     * 将 item 转化为 sql片段
     * @param item param/value/field
     * @param wrapper owner
     * @return sql info
     */
    protected String wrapSql(Item item, AbstractQueryWrapper<?,?> wrapper){
        FieldItem fieldItem = (FieldItem)item;
        String tbAlias = fieldItem.getAlias();
        StringBuilder fieldToSql = new StringBuilder();
        if(StringUtils.isEmpty(tbAlias)){
            tbAlias = ConstValue.MAIN_ALIAS;
        }else{
            fieldToSql.append(tbAlias).append(ConstValue.DOT);
        }
        TableMetaInfo tb = wrapper.aliasTables.get(tbAlias);
        //if tb is null then some alias not in sql,please check sql's alias
        EntryFieldInfo fieldInfo = tb.getFieldInfos().get(fieldItem.getName());
        Assert.notNull(fieldInfo,"column can't find in " + tb.getTableName() + " with field [ "+tbAlias+"." + fieldItem.getName()+"]");
        fieldToSql.append(fieldInfo.getColumn());
        return fieldToSql.toString();
    }
}
