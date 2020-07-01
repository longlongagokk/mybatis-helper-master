package com.mybatishelper.core.wrapper.update;

import com.mybatishelper.core.base.FieldValue;
import com.mybatishelper.core.base.meta.FieldWithValue;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.wrapper.IUpdateWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractConditionWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;
import com.mybatishelper.core.wrapper.factory.PropertyConditionWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpdateWrapper<C extends AbstractConditionWrapper>
        extends AbstractQueryWrapper<C,UpdateWrapper<C>>
        implements IUpdateWrapper<UpdateWrapper<C>,C> {
    List<FieldValue> updateItems;
    public UpdateWrapper(C where){
        super(where,new ArrayList<>(1<<1));
        this.updateItems = new ArrayList<>(1<<3);
    }
    @Override
    public UpdateWrapper<C> set(FieldValue... items) {
        Assert.notNull(items,"update items can not be empty");
        Collections.addAll(updateItems, items);
        return this;
    }
    @Override
    public UpdateWrapper<C> set(String fieldWithAlias, Object value) {
        return set(FieldWithValue.withParamValue(fieldWithAlias,value));
    }
}
