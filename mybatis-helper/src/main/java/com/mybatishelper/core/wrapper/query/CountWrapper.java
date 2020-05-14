package com.mybatishelper.core.wrapper.query;

import com.mybatishelper.core.wrapper.ICountWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractConditionWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractJoinerWrapper;
import com.mybatishelper.core.wrapper.factory.PropertyConditionWrapper;

import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")
public class CountWrapper<C extends AbstractConditionWrapper>
        extends AbstractJoinerWrapper<C,CountWrapper<C>>
    implements ICountWrapper<CountWrapper<C>,C>
{
    public CountWrapper(C where){
        super(where,new ArrayList<>(AbstractConditionWrapper.DEFAULT_CONDITION_ELEMENTS_SIZE));
    }
    public CountWrapper(C where,AbstractJoinerWrapper absWrapper){
        super(where,absWrapper);
    }
    public static ICountWrapper<CountWrapper<PropertyConditionWrapper>, PropertyConditionWrapper> build(){
        return new CountWrapper<>(new PropertyConditionWrapper());
    }
}
