package club.yourbatis.hi.wrapper.condition;

import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.consts.ConstValue;
import club.yourbatis.hi.enums.ConditionType;
import club.yourbatis.hi.wrapper.bridge.AbstractQueryWrapper;

public class LinkSelectItem extends SimpleConditionItem {
    private LinkSelectItem(ConditionType type, Item... items) {
        super(type, items);
    }
    public static LinkSelectItem valueOf(ConditionType type, Item... items) {
        return new LinkSelectItem(type,items);
    }

    @Override
    public String createSql(AbstractQueryWrapper wrapper) {
        if (items == null || items.length < 2) {
            throw new IllegalArgumentException("argument is not math with condition["+type.getOpera()+"]");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(wrapSql(items[0], wrapper));
        sb.append(ConstValue.BLANK);
        sb.append(ConstValue.BACKTRICKS);
        sb.append(items[1]);
        sb.append(ConstValue.BACKTRICKS);
        return sb.toString();
    }
}
