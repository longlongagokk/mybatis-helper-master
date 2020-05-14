package com.mybatishelper.core.wrapper.factory;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.wrapper.bridge.AbstractConditionWrapper;

import java.util.Collection;

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
}
