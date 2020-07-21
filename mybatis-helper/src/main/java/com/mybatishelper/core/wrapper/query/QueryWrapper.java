package com.mybatishelper.core.wrapper.query;

import com.mybatishelper.core.base.meta.GroupInfo;
import com.mybatishelper.core.base.meta.HavingInfo;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.util.StringUtils;
import com.mybatishelper.core.wrapper.IGroupBy;
import com.mybatishelper.core.wrapper.IHaving;
import com.mybatishelper.core.wrapper.bridge.AbstractConditionWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class QueryWrapper<C extends AbstractConditionWrapper>
        extends AbstractQueryWrapper<C, QueryWrapper<C>> implements IGroupBy<QueryWrapper<C>>, IHaving<QueryWrapper<C>> {
    List<GroupInfo> groupBys;
    HavingInfo havingInfo;
    public QueryWrapper(C where){
        super(where,new ArrayList<>(AbstractConditionWrapper.DEFAULT_CONDITION_ELEMENTS_SIZE));
        groupBys = new ArrayList<>(1<<2);
    }
    public QueryWrapper(C where,AbstractQueryWrapper absWrapper) {
        super(where,absWrapper);
    }

    @Override
    public QueryWrapper<C> groupBy(String fields) {
        if(StringUtils.isEmpty(fields)){
            return this;
        }
        String[] split = fields.split(",");
        for (String s : split) {
            this.groupBys.add(GroupInfo.withField(s));
        }
        return this;
    }

    @Override
    public QueryWrapper<C> groupBy(GroupInfo... items) {
        Assert.notEmpty(items,"items can not be empty");
        Collections.addAll(groupBys, items);
        return this;
    }
    @Override
    public QueryWrapper<C> having(String originalSql, Object... params) {
        this.havingInfo = HavingInfo.valueOf(originalSql,params);
        return this;
    }
}
