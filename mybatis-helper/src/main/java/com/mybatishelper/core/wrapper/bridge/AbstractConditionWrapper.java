package com.mybatishelper.core.wrapper.bridge;

import com.mybatishelper.core.base.FieldValue;
import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.param.ParamItem;
import com.mybatishelper.core.base.param.ValueItem;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.enums.ItemType;
import com.mybatishelper.core.util.LinkStack;
import com.mybatishelper.core.wrapper.IConditioner;
import com.mybatishelper.core.wrapper.ISqlSegment;
import com.mybatishelper.core.wrapper.IWrapper;
import com.mybatishelper.core.wrapper.factory.FlexibleConditionWrapper;
import com.mybatishelper.core.wrapper.factory.PropertyConditionWrapper;
import com.mybatishelper.core.wrapper.seg.BetweenConditionSeg;
import com.mybatishelper.core.wrapper.seg.InsConditionSeg;
import com.mybatishelper.core.wrapper.seg.SimpleConditionSeg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractConditionWrapper<L,R, S extends AbstractConditionWrapper>
        implements IWrapper, IConditioner<L,R,S>,Cloneable {
    public final static int DEFAULT_CONDITION_ELEMENTS_SIZE = 1 << 3;
    AbstractQueryWrapper caller;
    private StringBuilder where = new StringBuilder();

    protected String paramAlias;
    protected List<Item> params;
    protected List<ISqlSegment> fields;
    protected LinkStack<ConditionType> closure;
    private volatile boolean barrier;
    private boolean sqlCreated;
    public AbstractConditionWrapper(int paramSize) {
        this(paramSize,null);
    }
    public AbstractConditionWrapper(int paramSize,AbstractQueryWrapper caller) {
        this(paramSize,caller,"");
    }
    public AbstractConditionWrapper(int paramSize,AbstractQueryWrapper caller,String paramAlias) {
        reset(paramSize,caller,paramAlias);
    }
    void reset(int paramSize,AbstractQueryWrapper caller,String paramAlias){
        this.params = new ArrayList<>(paramSize);
        this.fields = new ArrayList<>(params.size());
        this.caller = caller;
        this.paramAlias = paramAlias;
        this.sqlCreated = false;
        this.barrier = true;
        //this.where.delete(0,this.where.length());
        this.where = new StringBuilder();
        this.closure = new LinkStack<>();
        closure.push(ConditionType.AND);
    }
    public AbstractConditionWrapper(AbstractConditionWrapper copy) {
        this(DEFAULT_CONDITION_ELEMENTS_SIZE);
        this.paramAlias = copy.paramAlias;
        this.fields = copy.fields;
        this.closure = copy.closure;
        this.params = copy.params;
        this.barrier = copy.barrier;
        this.caller = copy.caller;
    }
    public String getConditionSql() {
        if(sqlCreated){
            return where.toString();
        }
        sqlCreated = true;
        for(ISqlSegment e:fields){
            where.append(e.createSql(caller));
        }
        return where.toString();
    }

    @Override
    public S eq(L left,R right) {
        return exchangeItems(ConditionType.EQ, left, Collections.singletonList(right));
    }
    @Override
    public S eq(FieldValue fv) {
        return exchangeItems(ConditionType.EQ,fv);
    }

    @Override
    public S gt(L left,R right) {
        return exchangeItems(ConditionType.GT, left,Collections.singletonList(right));
    }
    @Override
    public S gt(FieldValue fv) {
        return exchangeItems(ConditionType.GT,fv);
    }

    @Override
    public S lt(L left,R right) {
        return exchangeItems(ConditionType.LT, left,Collections.singletonList(right));
    }
    @Override
    public S lt(FieldValue fv) {
        return exchangeItems(ConditionType.LT,fv);
    }

    @Override
    public S ge(L left,R right) {
        return exchangeItems(ConditionType.GE, left,Collections.singletonList(right));
    }
    @Override
    public S ge(FieldValue fv) {
        return exchangeItems(ConditionType.GE,fv);
    }

    @Override
    public S le(L left,R right) {
        return exchangeItems(ConditionType.LE, left,Collections.singletonList(right));
    }
    @Override
    public S le(FieldValue fv) {
        return exchangeItems(ConditionType.LE,fv);
    }

    @Override
    public S neq(L left,R right) {
        return exchangeItems(ConditionType.NEQ, left,Collections.singletonList(right));
    }
    @Override
    public S neq(FieldValue fv) {
        return exchangeItems(ConditionType.NEQ,fv);
    }

    @Override
    public S like(L left,R right) {
        return exchangeItems(ConditionType.LIKE, left,Collections.singletonList(right));
    }
    @Override
    public S like(FieldValue fv) {
        return exchangeItems(ConditionType.LIKE,fv);
    }

    @Override
    public S isNull(L left) {
        return exchangeItems(ConditionType.ISNULL,left,Collections.emptyList());
    }
    @Override
    public S notNull(L left) {
        return exchangeItems(ConditionType.NOTNULL,left,Collections.emptyList());
    }

    @Override
    public S in(L left, Collection<?> values) {
        return exchangeItems(ConditionType.IN,left,values);
    }

    @Override
    public S notIn(L left, Collection<?> values) {
        return exchangeItems(ConditionType.NOTIN,left,values);
    }

    @Override
    public S between(L left, R r0, R r1) {
        List<R> list = new ArrayList<>();
        list.add(r0);
        list.add(r1);
        return exchangeItems(ConditionType.BETWEEN,left,list);
    }
    protected S toTheMoon(ConditionType type, Item...items) {
        switch (type){
            case EQ:
            case NEQ:
            case GE:
            case GT:
            case LE:
            case LT:
            case LIKE:
            case ISNULL:
            case NOTNULL:
            case DONOTHINE:
                addElement(SimpleConditionSeg.valueOf(type,items));
                break;
            case IN:
            case NOTIN:
                if(items.length > 1) {
                    addElement(InsConditionSeg.valueOf(type, items));
                }
                break;
            case OR:
            case AND:
            case CLOSURE:
            case LEFTWRAPPER:
            case RIGHTWRAPPER:
                break;
            case BETWEEN:
                addElement(BetweenConditionSeg.valueOf(items));
                break;
        }
        return (S)this;
    }
    @Override
    public S where(ConditionType type,L left, Collection<?> right) {
        return exchangeItems(type,left,right);
    }

    @Override
    public S and(Consumer<S> consumer) {
        return closure(consumer,ConditionType.AND);
    }

    @Override
    public S or(Consumer<S> consumer) {
        return closure(consumer,ConditionType.OR);
    }

    private S closure(Consumer<S> consumer,ConditionType type) {
        if(!barrier){
            barrier = true;
            final ConditionType _closureType = closure.peek();
            fields.add(w -> _closureType.getOpera());
        }
        fields.add(w -> ConditionType.LEFTWRAPPER.getOpera());
        closure.push(type);
        consumer.accept((S)this);
        fields.add(w -> ConditionType.RIGHTWRAPPER.getOpera());
        closure.pop();
        return (S)this;
    }

    private void addElement(ISqlSegment element){
        if(barrier){
            barrier = false;
        }else{
            final ConditionType _closureType = closure.peek();
            fields.add(w -> _closureType.getOpera());
        }
        fields.add(element);
    }
    protected Item wrapItemIfHasParam(Item it){
        if(it.getType() == ItemType.PARAM){
            return wrapParamByValue(it.getValue());
        }
        return it;
    }
    protected Item wrapParamByValue(Object value){
        int index = params.size();
        params.add(ValueItem.valueOf(value));
        return ParamItem.valueOf(paramAlias + "params",index);
    }

    protected abstract S exchangeItems(ConditionType type,L left, Collection<?> rights);
    private S exchangeItems(ConditionType type, FieldValue fv){
        Item right = wrapItemIfHasParam(fv.getRight());
        return toTheMoon(type,fv.getLeft(),right);
    }

    @Override
    protected S clone() throws CloneNotSupportedException {
        return (S)super.clone();
    }


    //*****************************switch*************************************//

    public PropertyConditionWrapper d() {
        PropertyConditionWrapper wrapper = new PropertyConditionWrapper(this);
        this.barrier = false;
        return wrapper;
    }

    public FlexibleConditionWrapper f() {
        FlexibleConditionWrapper wrapper = new FlexibleConditionWrapper(this);
        this.barrier = false;
        return wrapper;
    }





}
