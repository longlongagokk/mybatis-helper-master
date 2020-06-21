package com.mybatishelper.core.wrapper.query;

import com.mybatishelper.core.base.Page;
import com.mybatishelper.core.base.field.OrderField;
import com.mybatishelper.core.base.field.SelectField;
import com.mybatishelper.core.wrapper.IOrder;
import com.mybatishelper.core.wrapper.IPager;
import com.mybatishelper.core.wrapper.ISelectorWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractConditionWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;
import com.mybatishelper.core.wrapper.factory.PropertyConditionWrapper;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

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
    List<SelectField> selectItems;
    List<OrderField> orderItems;
    @Getter
    Page page;
    protected boolean selectMain;
    protected boolean lock;
    public SelectWrapper(C where){
        super(where,new ArrayList<>(AbstractConditionWrapper.DEFAULT_CONDITION_ELEMENTS_SIZE));
        this.selectItems = new ArrayList<>(AbstractConditionWrapper.DEFAULT_CONDITION_ELEMENTS_SIZE);
        this.selectMain = false;
        orderItems = new ArrayList<>(1<<3);
    }
    public static DefaultSelectWrapper build(){
        return new DefaultSelectWrapper();
    }
    public static class DefaultSelectWrapper extends SelectWrapper<PropertyConditionWrapper>
    {
        public DefaultSelectWrapper(){
            super(new PropertyConditionWrapper());
        }
    }
    @Override
    public Collection<SelectField> selects() {
        return this.selectItems;
    }

    @Override
    public SelectWrapper<C> select(SelectField... fields) {
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
            this.selectItems.add(SelectField.valueOf(s));
        }
        return this;
    }

    @Override
    public SelectWrapper<C> selectMain(boolean selectMain) {
        this.selectMain = selectMain;
        return this;
    }

    @Override
    public SelectWrapper<C> orderBy(OrderField...fields){
        Assert.notEmpty(fields,"items can not be empty");
        Collections.addAll(orderItems, fields);
        return this;
    }
    @Override
    public SelectWrapper<C> orderBy(String strings){
        if(StringUtils.isEmpty(strings)){
            return this;
        }
        String[] split = strings.split(",");
        for (String s : split) {
            this.orderItems.add(OrderField.valueOf(s));
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
