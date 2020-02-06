package club.yourbatis.hi.wrapper.factory;

import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.base.param.FieldItem;
import club.yourbatis.hi.enums.ConditionType;
import club.yourbatis.hi.wrapper.condition.AbstractConditionWrapper;

import java.util.Collection;

public class EnumConditionWrapper extends AbstractConditionWrapper<Enum, Object, EnumConditionWrapper> {
    public EnumConditionWrapper(){
        super(DEFAULT_CONDITION_ELEMENTS_SIZE,null,"");
    }
    public EnumConditionWrapper(AbstractConditionWrapper e){
        super(e);
    }
    @Override
    protected EnumConditionWrapper exchangeItems(ConditionType type, Enum left, Collection<?> rights) {
        Item[] wraps = new Item[rights.size() + 1];
        wraps[0] = FieldItem.valueOf(left.name());
        int i = 0;
        for (Object obj : rights) {
            wraps[++i] = wrapParamByValue(obj);
        }
        return tothemoon(type, wraps);
    }
}
