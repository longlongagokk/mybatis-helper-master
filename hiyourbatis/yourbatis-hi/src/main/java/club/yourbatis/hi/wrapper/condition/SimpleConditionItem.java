package club.yourbatis.hi.wrapper.condition;

import club.yourbatis.hi.base.Field;
import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.enums.ConditionType;
import club.yourbatis.hi.enums.ItemType;
import club.yourbatis.hi.util.Assert;
import club.yourbatis.hi.util.TableInfoHelper;

public class SimpleConditionItem implements LinkItem {
    protected ConditionType type;
    protected Item[] items;

    SimpleConditionItem(ConditionType type, Item... items) {
        this.type = type;
        this.items = items;
    }

    public static SimpleConditionItem valueOf(ConditionType type, Item... items) {
        return new SimpleConditionItem(type,items);
    }

    @Override
    public String createSql(AbstractQueryWrapper wrapper) {
        if (items == null || items.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(wrapSql(items[0],wrapper));
        sb.append(type.getOpera());
        for(int i = 1;i<items.length;++i){
            sb.append(wrapSql(items[i],wrapper));
        }
        return sb.toString();
    }
    String wrapSql(Item it, AbstractQueryWrapper wrapper){
        if(it.getType() == ItemType.FIELD){
            Field field = TableInfoHelper.getColumnItemByFieldItem(wrapper.aliases,(Field)it.getValue());
            Assert.notNull(field,"can not find column with " + it.toString());
            return field.getFullName();
        }
        return it.toString();
    }
}
