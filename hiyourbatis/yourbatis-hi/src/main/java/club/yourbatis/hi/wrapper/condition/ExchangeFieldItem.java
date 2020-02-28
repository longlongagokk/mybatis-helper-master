package club.yourbatis.hi.wrapper.condition;

import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.enums.ConditionType;
import club.yourbatis.hi.wrapper.bridge.AbstractQueryWrapper;

public class ExchangeFieldItem extends SimpleConditionItem {
    private ExchangeFieldItem(ConditionType type, Item item) {
        super(type, item);
    }
    public static ExchangeFieldItem valueOf(ConditionType type, Item item) {
        return new ExchangeFieldItem(type,item);
    }

    @Override
    public String createSql(AbstractQueryWrapper wrapper) {
        if (items == null || items.length < 1) {
            throw new IllegalArgumentException("argument is not math with condition["+type.getOpera()+"]");
        }
        return wrapSql(items[0], wrapper);
    }
}
