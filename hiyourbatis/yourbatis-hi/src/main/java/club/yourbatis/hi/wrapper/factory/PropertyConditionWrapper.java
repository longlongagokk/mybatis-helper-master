package club.yourbatis.hi.wrapper.factory;

import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.base.param.FieldItem;
import club.yourbatis.hi.enums.ConditionType;
import club.yourbatis.hi.wrapper.bridge.AbstractConditionWrapper;

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
