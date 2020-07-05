package com.mybatishelper.core.wrapper.seg;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.meta.SortInfo;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.enums.ItemType;
import com.mybatishelper.core.wrapper.bridge.AbsSqlSegment;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;

public class LinkOrderSeg extends AbsSqlSegment<SortInfo> {
    private LinkOrderSeg(ConditionType type, SortInfo... sortInfos) {
        super(type, sortInfos);
    }
    public static LinkOrderSeg valueOf(SortInfo... sortInfos) {
        return new LinkOrderSeg(ConditionType.DO_NOTHING,sortInfos);
    }

    @Override
    public String createSql(AbstractQueryWrapper wrapper) {
        if(items == null || items.length == 0){
            return "";
        }
        StringBuilder sortSql = new StringBuilder(" order by ");
        for(SortInfo item:items){
            Item it = item.getValue();
            if(it.getType() == ItemType.FIELD){
                sortSql
                        .append(wrapSql(it,wrapper))
                        .append(ConstValue.BLANK)
                        .append(item.getOrder())
                        .append(ConstValue.BLANK);
            }else{
                sortSql.append(it.toString());
            }
            sortSql.append(ConstValue.COMMA);
        }
        sortSql.deleteCharAt(sortSql.length() - 1);
        return sortSql.toString();
    }
}
