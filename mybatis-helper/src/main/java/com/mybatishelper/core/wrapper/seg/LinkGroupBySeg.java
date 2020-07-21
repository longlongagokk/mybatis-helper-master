package com.mybatishelper.core.wrapper.seg;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.meta.GroupInfo;
import com.mybatishelper.core.base.meta.SortInfo;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.enums.ItemType;
import com.mybatishelper.core.util.CollectionUtils;
import com.mybatishelper.core.wrapper.bridge.AbsSqlSegment;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;

import java.util.Arrays;
import java.util.List;

public class LinkGroupBySeg extends AbsSqlSegment<GroupInfo> {
    private LinkGroupBySeg(ConditionType type, GroupInfo... groupInfos) {
        super(type, groupInfos);
    }
    public static LinkGroupBySeg valueOf(List<GroupInfo> groupInfos) {
        GroupInfo[] gis = CollectionUtils.isEmpty(groupInfos) ? null : groupInfos.toArray(new GroupInfo[0]);
        return new LinkGroupBySeg(ConditionType.DO_NOTHING,gis);
    }

    @Override
    public String createSql(AbstractQueryWrapper wrapper) {
        if(CollectionUtils.isEmpty(items)){
            return "";
        }
        StringBuilder sql = new StringBuilder(" GROUP BY ");
        for(GroupInfo item:items){
            Item it = item.getValue();
            sql.append(ConstValue.LEFT_PARENTHESES);
            if(it.getType() == ItemType.FIELD){
                sql.append(wrapSql(it,wrapper));
            }else{
                sql.append(it.toString());
            }
            sql.append(ConstValue.RIGHT_PARENTHESES);
            sql.append(ConstValue.COMMA);
        }
        sql.deleteCharAt(sql.length() - 1);
        return sql.toString();
    }
}
