package club.yourbatis.hi.wrapper.seg;

import club.yourbatis.hi.base.Field;
import club.yourbatis.hi.base.field.SelectField;
import club.yourbatis.hi.consts.ConstValue;
import club.yourbatis.hi.enums.ConditionType;
import club.yourbatis.hi.wrapper.bridge.AbsSqlSegment;
import club.yourbatis.hi.wrapper.bridge.AbstractQueryWrapper;

public class LinkSelectSeg extends AbsSqlSegment<SelectField> {
    private LinkSelectSeg(ConditionType type, SelectField... selectFields) {
        super(type, selectFields);
    }
    public static LinkSelectSeg valueOf(SelectField... selectFields) {
        return new LinkSelectSeg(ConditionType.DONOTHINE,selectFields);
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
