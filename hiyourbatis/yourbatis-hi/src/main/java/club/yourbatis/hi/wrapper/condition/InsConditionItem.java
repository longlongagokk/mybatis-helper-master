package club.yourbatis.hi.wrapper.condition;

import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.consts.ConstValue;
import club.yourbatis.hi.enums.ConditionType;
import club.yourbatis.hi.wrapper.bridge.AbstractQueryWrapper;

public class InsConditionItem extends SimpleConditionItem {
    private InsConditionItem(ConditionType type, Item... items) {
        super(type, items);
    }
    public static InsConditionItem valueOf(ConditionType type, Item... items) {
        return new InsConditionItem(type,items);
    }

    @Override
    public String createSql(AbstractQueryWrapper wrapper) {
        if (items == null || items.length < 2) {
            throw new IllegalArgumentException("argument is not math with condition["+type.getOpera()+"]");
        }
        StringBuilder sb = new StringBuilder(wrapSql(items[0], wrapper));
        sb.append(type.getOpera());
        sb.append(ConditionType.LEFTWRAPPER.getOpera());
        for (int i = 1; i < items.length - 1; ++i) {
            sb.append(wrapSql(items[i], wrapper));
            sb.append(ConstValue.COMMA);
        }
        sb.append(wrapSql(items[items.length - 1], wrapper));
        sb.append(ConditionType.RIGHTWRAPPER.getOpera());
        return sb.toString();
    }
}
