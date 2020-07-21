package com.mybatishelper.core.wrapper.seg;

import com.mybatishelper.core.base.meta.HavingInfo;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.util.StringUtils;
import com.mybatishelper.core.wrapper.bridge.AbsSqlSegment;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;

public class LinkHavingSeg extends AbsSqlSegment<Object> {
    private HavingInfo havingInfo;
    private LinkHavingSeg(HavingInfo havingInfo) {
        super(ConditionType.DO_NOTHING, null);
        this.havingInfo = havingInfo;
    }
    public static LinkHavingSeg valueOf(HavingInfo havingInfo) {
        return new LinkHavingSeg(havingInfo);
    }

    @Override
    public String createSql(AbstractQueryWrapper wrapper) {
        if(null == havingInfo || StringUtils.isEmpty(havingInfo.getHavingSql())){
            return "";
        }
        StringBuilder sql = new StringBuilder(" HAVING (");

        int qi = 0,ln = havingInfo.getHavingSql().length(),pi = 0;
        while(qi != ln){
            char c = havingInfo.getHavingSql().charAt(qi++);
            if(c == '?'){
                sql.append("#{havingInfo.params[").append(pi++).append("].value}");
            }else{
                sql.append(c);
            }
        }
        sql.append(")");
        return sql.toString();
    }
}
