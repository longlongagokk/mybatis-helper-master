package club.yourbatis.hi.wrapper.factory;

import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.enums.ConditionType;
import club.yourbatis.hi.wrapper.bridge.AbstractConditionWrapper;
import lombok.Getter;

import java.util.Collection;

@Getter
public class FlexibleConditionWrapper extends AbstractConditionWrapper<Item,Item, FlexibleConditionWrapper> {
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
}
