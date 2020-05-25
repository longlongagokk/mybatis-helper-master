package com.mybatishelper.core.wrapper.seg;

import com.mybatishelper.core.base.Field;
import com.mybatishelper.core.base.field.SelectField;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.wrapper.bridge.AbsSqlSegment;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;

public class LinkSelectSeg extends AbsSqlSegment<SelectField> {
    private LinkSelectSeg(ConditionType type, SelectField... selectFields) {
        super(type, selectFields);
    }
    public static LinkSelectSeg valueOf(SelectField... selectFields) {
        return new LinkSelectSeg(ConditionType.DO_NOTHING,selectFields);
    }

    @Override
    public String createSql(AbstractQueryWrapper wrapper) {
        if(items == null || items.length == 0){
            return "";
        }
        StringBuilder fieldSql = new StringBuilder();
        for(SelectField field:items){
            fieldSql
                    .append(wrapSql(field,wrapper))
                    .append(ConstValue.COMMA)
            ;
        }
        return fieldSql.toString();
    }
    @Override
    protected Field wrapField(Field oriField, String column){
        SelectField of = (SelectField)oriField;
        return SelectField.valueOf(of.getAlias(),column,of.getColumnAlias(),of.isOriginal());
    }
}
