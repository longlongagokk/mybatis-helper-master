package club.yourbatis.hi.wrapper.factory;

import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.base.param.FieldItem;
import club.yourbatis.hi.enums.ConditionType;
import club.yourbatis.hi.wrapper.condition.AbstractConditionWrapper;
import club.yourbatis.hi.wrapper.condition.AbstractQueryWrapper;

import java.util.Collection;

/**
 * the default condition wrapper
 */
public class StringConditionWrapper extends AbstractConditionWrapper<String,Object, StringConditionWrapper> {
    public StringConditionWrapper(){
        super(DEFAULT_CONDITION_ELEMENTS_SIZE,null,"");
    }
    public StringConditionWrapper(AbstractConditionWrapper e){
        super(e);
    }
    @Override
    protected StringConditionWrapper exchangeItems(ConditionType type, String left, Collection<?> rights) {
        Item[] wraps = new Item[rights.size() + 1];
        wraps[0] = FieldItem.valueOf(left);
        int i = 0;
        for (Object obj : rights) {
            wraps[++i] = wrapParamByValue(obj);
        }
        return tothemoon(type, wraps);
    }
}
