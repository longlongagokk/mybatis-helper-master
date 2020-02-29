package club.yourbatis.hi.wrapper.seg;

import club.yourbatis.hi.base.Field;
import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.base.field.CompareField;
import club.yourbatis.hi.enums.ConditionType;
import club.yourbatis.hi.enums.ItemType;
import club.yourbatis.hi.wrapper.bridge.AbsSqlSegment;
import club.yourbatis.hi.wrapper.bridge.AbstractQueryWrapper;

public class SimpleConditionSeg extends AbsSqlSegment<Item> {
    SimpleConditionSeg(ConditionType type, Item... items) {
        super(type,items);
    }

    public static SimpleConditionSeg valueOf(ConditionType type, Item... items) {
        return new SimpleConditionSeg(type,items);
    }

    @Override
    protected Field wrapField(Field oriField, String column) {
        return CompareField.valueOf(oriField.getAlias(),column,oriField.isOriginal());
    }

    @Override
    public String createSql(AbstractQueryWrapper wrapper) {
        if (items == null || items.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(wrapItemSql(items[0],wrapper));
        sb.append(type.getOpera());
        for(int i = 1;i<items.length;++i){
            sb.append(wrapItemSql(items[i],wrapper));
        }
        return sb.toString();
    }

    String wrapItemSql(Item it, AbstractQueryWrapper<?, ?> wrapper){
        if(it.getType() == ItemType.FIELD){
            return wrapSql((Field)it.getValue(),wrapper);
        }
        return it.toString();
    }
}
