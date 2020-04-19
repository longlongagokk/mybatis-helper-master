package club.yourbatis.hi.wrapper.query;

import club.yourbatis.hi.wrapper.ICountWrapper;
import club.yourbatis.hi.wrapper.bridge.AbstractConditionWrapper;
import club.yourbatis.hi.wrapper.bridge.AbstractJoinerWrapper;
import club.yourbatis.hi.wrapper.factory.PropertyConditionWrapper;

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
