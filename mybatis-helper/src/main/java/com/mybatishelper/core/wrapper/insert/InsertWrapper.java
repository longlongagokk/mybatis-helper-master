package com.mybatishelper.core.wrapper.insert;

import com.mybatishelper.core.base.Page;
import com.mybatishelper.core.base.meta.GroupInfo;
import com.mybatishelper.core.base.meta.HavingInfo;
import com.mybatishelper.core.base.meta.InsertInfo;
import com.mybatishelper.core.base.meta.SortInfo;
import com.mybatishelper.core.cache.TableMetaInfo;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.util.StringUtils;
import com.mybatishelper.core.util.TableInfoHelper;
import com.mybatishelper.core.wrapper.IGroupBy;
import com.mybatishelper.core.wrapper.IHaving;
import com.mybatishelper.core.wrapper.IInsertWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractConditionWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;
import lombok.Getter;

import java.util.*;

public class InsertWrapper<C extends AbstractConditionWrapper>
        extends AbstractQueryWrapper<C, InsertWrapper<C>>
        implements IInsertWrapper<InsertWrapper<C>,C>,
        IGroupBy<InsertWrapper<C>>, IHaving<InsertWrapper<C>> {
    List<InsertInfo> insertItems;
    Set<InsertInfo> insertInfos;
    TableMetaInfo insertTable;
    List<SortInfo> sortItems;
    List<GroupInfo> groupBys;
    HavingInfo havingInfo;
    @Getter
    Page page;
    public InsertWrapper(C where){
        super(where,new ArrayList<>(1<<1));
        this.insertInfos = new LinkedHashSet<>(1<<4);
        sortItems = new ArrayList<>(1<<3);
        groupBys = new ArrayList<>(1<<2);
    }

    @Override
    public InsertWrapper<C> insertInto(Class<?> tbClass) {
        this.insertTable = TableInfoHelper.getTableInfoFromEntityClass(tbClass);
        return this;
    }

    @Override
    public InsertWrapper<C> select(InsertInfo... insertInfos) {
        Assert.notNull(insertInfos,"insert items can not be empty");
        Collections.addAll(this.insertInfos, insertInfos);
        return this;
    }

    @Override
    public InsertWrapper<C> fieldWithValue(String fieldWithAlias, Object value) {
        return select(InsertInfo.withFieldParam(fieldWithAlias,value));
    }
    @Override
    public InsertWrapper<C> orderBy(SortInfo...sortInfos){
        Assert.notEmpty(sortInfos,"items can not be empty");
        Collections.addAll(sortItems, sortInfos);
        return this;
    }
    @Override
    public InsertWrapper<C> orderBy(String strings){
        if(StringUtils.isEmpty(strings)){
            return this;
        }
        String[] split = strings.split(",");
        for (String s : split) {
            this.sortItems.add(SortInfo.withField(s));
        }
        return this;
    }
    @Override
    public InsertWrapper<C> page(Page page){
        this.page = page;
        return this;
    }

    @Override
    public InsertWrapper<C> groupBy(String fields) {
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
    public InsertWrapper<C> groupBy(GroupInfo... items) {
        Assert.notEmpty(items,"items can not be empty");
        Collections.addAll(groupBys, items);
        return this;
    }
    @Override
    public InsertWrapper<C> having(String originalSql, Object... params) {
        this.havingInfo = HavingInfo.valueOf(originalSql,params);
        return this;
    }
}
