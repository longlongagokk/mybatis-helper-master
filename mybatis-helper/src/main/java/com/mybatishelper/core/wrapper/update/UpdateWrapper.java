package com.mybatishelper.core.wrapper.update;

import com.mybatishelper.core.base.Field;
import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.meta.ItemPar;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.base.param.ParamItem;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.wrapper.IUpdateWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractConditionWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;

import java.util.*;

public class UpdateWrapper<C extends AbstractConditionWrapper>
        extends AbstractQueryWrapper<C,UpdateWrapper<C>>
        implements IUpdateWrapper<UpdateWrapper<C>,C> {
    List<ItemPar> updateItems;
    Set<ItemPar> itemPars;
    public UpdateWrapper(C where){
        super(where,new ArrayList<>(1<<1));
        this.itemPars = new LinkedHashSet<>(1<<3);
    }
    @SafeVarargs
    @Override
    public final UpdateWrapper<C> set(ItemPar... items) {
        Assert.notNull(items,"update items can not be empty");
        Collections.addAll(itemPars, items);
        return this;
    }
    @Override
    public UpdateWrapper<C> set(String fieldWithAlias, Object value) {
        Item<Field> key = FieldItem.valueOf(fieldWithAlias);
        Item val = ParamItem.valueOf(value);
        return set(ItemPar.valueOf(key,val));
    }
}
