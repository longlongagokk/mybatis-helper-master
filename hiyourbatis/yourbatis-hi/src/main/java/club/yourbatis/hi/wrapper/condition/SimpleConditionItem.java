package club.yourbatis.hi.wrapper.condition;

import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.enums.ConditionType;
import club.yourbatis.hi.wrapper.bridge.AbsLinkItem;

public class SimpleConditionItem extends AbsLinkItem {
    SimpleConditionItem(ConditionType type, Item... items) {
        super(type,items);
    }

    public static SimpleConditionItem valueOf(ConditionType type, Item... items) {
        return new SimpleConditionItem(type,items);
    }
}
