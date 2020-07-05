package com.mybatishelper.core.wrapper.query;

import com.mybatishelper.core.base.Page;
import com.mybatishelper.core.base.meta.SelectInfo;
import com.mybatishelper.core.base.meta.SortInfo;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.util.StringUtils;
import com.mybatishelper.core.wrapper.IOrder;
import com.mybatishelper.core.wrapper.IPager;
import com.mybatishelper.core.wrapper.ISelectorWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractConditionWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class SelectWrapper<C extends AbstractConditionWrapper>
        extends AbstractQueryWrapper<C, SelectWrapper<C>>
        implements ISelectorWrapper<SelectWrapper<C>,C>,
        IPager<SelectWrapper<C>>, IOrder<SelectWrapper<C>>
{
    /**
     * 选取的field字段
     */
    List<SelectInfo> selectItems;
    List<SortInfo> sortItems;

    @Getter
    Page page;
    protected boolean selectMain;
    protected boolean lock;
    public SelectWrapper(C where){
        super(where,new ArrayList<>(AbstractConditionWrapper.DEFAULT_CONDITION_ELEMENTS_SIZE));
        this.selectItems = new ArrayList<>(AbstractConditionWrapper.DEFAULT_CONDITION_ELEMENTS_SIZE);
        this.selectMain = false;
        sortItems = new ArrayList<>(1<<3);
    }
    @Override
    public Collection<SelectInfo> selects() {
        return this.selectItems;
    }

    @Override
    public SelectWrapper<C> select(SelectInfo... fields) {
        Assert.notEmpty(fields,"items can not be empty");
        Collections.addAll(selectItems, fields);
        return this;
    }
    @Override
    public SelectWrapper<C> select(String fields) {
        if(StringUtils.isEmpty(fields)){
            return this;
        }
        String[] split = fields.split(",");
        for (String s : split) {
            this.selectItems.add(SelectInfo.withField(s));
        }
        return this;
    }

    @Override
    public SelectWrapper<C> selectMain(boolean selectMain) {
        this.selectMain = selectMain;
        return this;
    }

    @Override
    public SelectWrapper<C> orderBy(SortInfo...sortInfos){
        Assert.notEmpty(sortInfos,"items can not be empty");
        Collections.addAll(sortItems, sortInfos);
        return this;
    }
    @Override
    public SelectWrapper<C> orderBy(String strings){
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
    public SelectWrapper<C> page(Page page){
        this.page = page;
        return this;
    }
    @Override
    public SelectWrapper<C> lock(boolean lock){
        this.lock = lock;
        return this;
    }
    @Override
    public <N extends SelectWrapper<C>> N back(){
        return (N)this;
    }
}
