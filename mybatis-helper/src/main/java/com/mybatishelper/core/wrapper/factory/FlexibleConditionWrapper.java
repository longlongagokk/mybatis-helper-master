package com.mybatishelper.core.wrapper.factory;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.wrapper.IQueryWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractConditionWrapper;
import com.mybatishelper.core.wrapper.query.QueryWrapper;
import com.mybatishelper.core.wrapper.seg.SimpleConditionSeg;
import lombok.Getter;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * customize
 */
@Getter
public class FlexibleConditionWrapper extends AbstractConditionWrapper<Item,Item, FlexibleConditionWrapper> {
    public FlexibleConditionWrapper(){
        super(DEFAULT_CONDITION_ELEMENTS_SIZE,null,"");
    }
    public FlexibleConditionWrapper(AbstractConditionWrapper e){
        super(e);
    }
    @Override
    protected FlexibleConditionWrapper exchangeItems(ConditionType type, Item left, Collection<?> items) {
        Item[] wraps = new Item[items.size() + 1];
        wraps[0] = wrapItemIfHasParam(left);
        int i = 0;
        for(Object item:items){
            wraps[++i] = wrapItemIfHasParam((Item)item);
        }
        return toTheMoon(type,wraps);
    }

    @Override
    public FlexibleConditionWrapper exists(Consumer<QueryWrapper<FlexibleConditionWrapper>> consumer) {
        QueryWrapper<FlexibleConditionWrapper> queryWrapper = SqlWrapperFactory.flex4Query();
        return super.existsFull(consumer,queryWrapper,ConditionType.EXISTS);
    }

    @Override
    public FlexibleConditionWrapper notExists(Consumer<QueryWrapper<FlexibleConditionWrapper>> consumer) {
        QueryWrapper<PropertyConditionWrapper> queryWrapper = SqlWrapperFactory.prop4Query();
        return super.existsFull(consumer,queryWrapper,ConditionType.NOT_EXISTS);
    }
}
