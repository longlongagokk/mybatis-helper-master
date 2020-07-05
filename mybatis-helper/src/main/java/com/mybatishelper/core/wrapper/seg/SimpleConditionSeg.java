package com.mybatishelper.core.wrapper.seg;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.enums.ItemType;
import com.mybatishelper.core.wrapper.bridge.AbsSqlSegment;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;

public class SimpleConditionSeg extends AbsSqlSegment<Item> {
    SimpleConditionSeg(ConditionType type, Item... items) {
        super(type,items);
    }

    public static SimpleConditionSeg valueOf(ConditionType type, Item... items) {
        return new SimpleConditionSeg(type,items);
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
            return wrapSql(it,wrapper);
        }
        return it.toString();
    }
}
