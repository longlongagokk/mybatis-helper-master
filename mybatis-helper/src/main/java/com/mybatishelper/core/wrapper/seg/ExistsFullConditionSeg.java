package com.mybatishelper.core.wrapper.seg;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.wrapper.bridge.AbsSqlSegment;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;

public class ExistsFullConditionSeg extends AbsSqlSegment<Item> {
    ExistsFullConditionSeg(ConditionType type, Item... items) {
        super(type,items);
    }

    public static ExistsFullConditionSeg valueOf(ConditionType type, Item... items) {
        return new ExistsFullConditionSeg(type,items);
    }

    @Override
    public String createSql(AbstractQueryWrapper wrapper) {
        String conditionSql = wrapper.getWhere().getExistsFullSql();
        Assert.notEmpty(conditionSql,"not found anny exists condition.");

        StringBuilder sb = new StringBuilder(type.getOpera());
        sb.append("(");
        sb.append(conditionSql);
        sb.append(")");
        return sb.toString();
    }
}
