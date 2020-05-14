package com.mybatishelper.core.wrapper.seg;

import com.mybatishelper.core.base.Field;
import com.mybatishelper.core.base.field.OrderField;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.wrapper.bridge.AbsSqlSegment;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;

public class LinkOrderSeg extends AbsSqlSegment<OrderField> {
    private LinkOrderSeg(ConditionType type, OrderField... orderFields) {
        super(type, orderFields);
    }
    public static LinkOrderSeg valueOf(OrderField... orderFields) {
        return new LinkOrderSeg(ConditionType.DONOTHINE,orderFields);
    }

    @Override
    public String createSql(AbstractQueryWrapper wrapper) {
        if(items == null || items.length == 0){
            return "";
        }
        StringBuilder fieldSql = new StringBuilder();
        for(OrderField field:items){
            fieldSql
                    .append(wrapSql(field,wrapper))
                    .append(ConstValue.COMMA)
            ;
        }
        return fieldSql.toString();
    }
    @Override
    protected Field wrapField(Field oriField, String column){
        OrderField of = (OrderField)oriField;
        return OrderField.valueOf(of.getAlias(),column,of.getOrder(),of.isOriginal());
    }
}
