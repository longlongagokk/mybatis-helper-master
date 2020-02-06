package club.yourbatis.hi.wrapper.condition;

import club.yourbatis.hi.base.FieldValue;
import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.base.param.ParamItem;
import club.yourbatis.hi.base.param.ValueItem;
import club.yourbatis.hi.enums.ConditionType;
import club.yourbatis.hi.enums.ItemType;
import club.yourbatis.hi.util.LinkStack;
import club.yourbatis.hi.wrapper.IConditioner;
import club.yourbatis.hi.wrapper.IWrapper;
import club.yourbatis.hi.wrapper.factory.EnumConditionWrapper;
import club.yourbatis.hi.wrapper.factory.FlexibleConditionWrapper;
import club.yourbatis.hi.wrapper.factory.StringConditionWrapper;

import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractConditionWrapper<L,R, S extends AbstractConditionWrapper>
        implements IWrapper, IConditioner<L,R,S>,Cloneable {
    public final static int DEFAULT_CONDITION_ELEMENTS_SIZE = 1 << 3;
    AbstractQueryWrapper caller;
    public final S _this = (S) this;
    private StringBuilder where = new StringBuilder();

    protected String paramAlias;
    protected List<Item> params;
    protected List<LinkItem> fields;
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
        for(LinkItem e:fields){
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
    protected S tothemoon(ConditionType type, Item...items) {
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
                addElement(SimpleConditionItem.valueOf(type,items));
                break;
            case IN:
            case NOTIN:
                if(items.length > 1) {
                    addElement(InsConditionItem.valueOf(type, items));
                }
                break;
            case OR:
            case AND:
            case CLOSURE:
            case LEFTWRAPPER:
            case RIGHTWRAPPER:
                break;
            case BETWEEN:
                addElement(BetweenConditionItem.valueOf(items));
                break;
        }
        return _this;
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
        consumer.accept(_this);
        fields.add(w -> ConditionType.RIGHTWRAPPER.getOpera());
        closure.pop();
        return _this;
    }

    private void addElement(LinkItem element){
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
        return tothemoon(type,fv.getLeft(),right);
    }

    @Override
    protected S clone() throws CloneNotSupportedException {
        return (S)super.clone();
    }


    //*****************************switch*************************************//

    public StringConditionWrapper d() {
        return new StringConditionWrapper(this);
    }

    public EnumConditionWrapper e() {
        return new EnumConditionWrapper(this);
    }

    public FlexibleConditionWrapper f() {
        return new FlexibleConditionWrapper(this);
    }





}
