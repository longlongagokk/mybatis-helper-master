package com.mybatishelper.core.wrapper.seg;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.meta.SelectInfo;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.enums.ItemType;
import com.mybatishelper.core.util.StringUtils;
import com.mybatishelper.core.wrapper.bridge.AbsSqlSegment;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;

public class LinkSelectSeg extends AbsSqlSegment<SelectInfo> {
    private LinkSelectSeg(ConditionType type, SelectInfo... selectInfos) {
        super(type, selectInfos);
    }
    public static LinkSelectSeg valueOf(SelectInfo... selectInfos) {
        return new LinkSelectSeg(ConditionType.DO_NOTHING,selectInfos);
    }

    @Override
    public String createSql(AbstractQueryWrapper wrapper) {
        if(items == null || items.length == 0){
            return "";
        }
        StringBuilder selectItemSql = new StringBuilder();
        for(SelectInfo item:items){
            Item it = item.getValue();
            if(it.getType() == ItemType.FIELD){
                selectItemSql.append(wrapSql(it,wrapper));
                if(!StringUtils.isEmpty(item.getColumnAlias())){
                    selectItemSql
                            .append(ConstValue.BLANK)
                            .append(ConstValue.BACK_TRICKS)
                            .append(item.getColumnAlias())
                            .append(ConstValue.BACK_TRICKS);
                }
            }else{
                selectItemSql.append(it.toString());
            }
            selectItemSql.append(ConstValue.COMMA);
        }
        return selectItemSql.toString();
    }
}
