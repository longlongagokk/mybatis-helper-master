package com.mybatishelper.core.wrapper.insert;

import com.mybatishelper.core.base.meta.InsertInfo;
import com.mybatishelper.core.cache.TableMetaInfo;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.util.TableInfoHelper;
import com.mybatishelper.core.wrapper.IInsertWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractConditionWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;

import java.util.*;

public class InsertWrapper<C extends AbstractConditionWrapper>
        extends AbstractQueryWrapper<C, InsertWrapper<C>>
        implements IInsertWrapper<InsertWrapper<C>,C> {
    List<InsertInfo> insertItems;
    Set<InsertInfo> insertInfos;
    TableMetaInfo insertTable;
    public InsertWrapper(C where){
        super(where,new ArrayList<>(1<<1));
        this.insertInfos = new LinkedHashSet<>(1<<3);
    }

    @Override
    public InsertWrapper<C> insertInto(Class<?> tbClass) {
        this.insertTable = TableInfoHelper.getTableInfoFromEntityClass(tbClass);
        return this;
    }

    @Override
    public InsertWrapper<C> select(InsertInfo... insertInfos) {
        Assert.notNull(insertInfos,"insert items can not be empty");
        Collections.addAll(this.insertInfos, insertInfos);
        return this;
    }

    @Override
    public InsertWrapper<C> fieldWithValue(String fieldWithAlias, Object value) {
        return select(InsertInfo.withFieldParam(fieldWithAlias,value));
    }
}
