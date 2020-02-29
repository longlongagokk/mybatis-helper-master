package club.yourbatis.hi.wrapper.seg;

import club.yourbatis.hi.base.Field;
import club.yourbatis.hi.base.field.OrderField;
import club.yourbatis.hi.consts.ConstValue;
import club.yourbatis.hi.enums.ConditionType;
import club.yourbatis.hi.wrapper.bridge.AbsSqlSegment;
import club.yourbatis.hi.wrapper.bridge.AbstractQueryWrapper;

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
