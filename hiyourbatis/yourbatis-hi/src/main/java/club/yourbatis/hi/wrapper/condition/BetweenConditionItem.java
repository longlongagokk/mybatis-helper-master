package club.yourbatis.hi.wrapper.condition;

import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.enums.ConditionType;

public class BetweenConditionItem extends SimpleConditionItem{
    private BetweenConditionItem(Item... items) {
        super(ConditionType.BETWEEN,items);
    }

    public static BetweenConditionItem valueOf(Item... items) {
        return new BetweenConditionItem(items[0],items[1],items[2]);
    }

    @Override
    public String createSql(AbstractQueryWrapper wrapper) {
        StringBuilder sb = new StringBuilder(ConditionType.LEFTWRAPPER.getOpera());
        sb.append(wrapSql(items[0],wrapper));
        sb.append(type.getOpera());
        sb.append(wrapSql(items[1],wrapper));
        sb.append(ConditionType.AND.getOpera());
        sb.append(wrapSql(items[2],wrapper));
        sb.append(ConditionType.RIGHTWRAPPER.getOpera());
        return sb.toString();
    }
}
