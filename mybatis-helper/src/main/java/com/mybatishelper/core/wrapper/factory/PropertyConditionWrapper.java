package com.mybatishelper.core.wrapper.factory;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.wrapper.IQueryWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractConditionWrapper;
import com.mybatishelper.core.wrapper.query.QueryWrapper;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * the default condition wrapper
 */
public class PropertyConditionWrapper extends AbstractConditionWrapper<String,Object, PropertyConditionWrapper> {
    public PropertyConditionWrapper(){
        super(DEFAULT_CONDITION_ELEMENTS_SIZE,null,"");
    }
    public PropertyConditionWrapper(AbstractConditionWrapper e){
        super(e);
    }
    @Override
    protected PropertyConditionWrapper exchangeItems(ConditionType type, String left, Collection<?> rights) {
        Item[] wraps = new Item[rights.size() + 1];
        wraps[0] = FieldItem.valueOf(left);
        int i = 0;
        for (Object obj : rights) {
            wraps[++i] = wrapParamByValue(obj);
        }
        return toTheMoon(type, wraps);
    }

    @Override
    public PropertyConditionWrapper exists(Consumer<QueryWrapper<PropertyConditionWrapper>> consumer) {
        QueryWrapper<PropertyConditionWrapper> queryWrapper = SqlWrapperFactory.prop4Query();
        return super.existsFull(consumer,queryWrapper,ConditionType.EXISTS);
    }

    @Override
    public PropertyConditionWrapper notExists(Consumer<QueryWrapper<PropertyConditionWrapper>> consumer) {
        QueryWrapper<PropertyConditionWrapper> queryWrapper = SqlWrapperFactory.prop4Query();
        return super.existsFull(consumer,queryWrapper,ConditionType.NOT_EXISTS);
    }
}
