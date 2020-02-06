package com.vitily.common.module;

import club.yourbatis.hi.base.Sortable;
import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.base.param.FieldItem;
import club.yourbatis.hi.enums.ConditionType;
import com.vitily.common.util.CommonUtil;
import com.vitily.common.util.StringUtil;
import club.yourbatis.hi.consts.ConstValue;
import club.yourbatis.hi.base.meta.PageInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class QueryInfo<T> {
    T entity;
    private PageInfo pageInfo;
    private List<WrapperQuery> conditionElements = new ArrayList<>();
    private Set<Sortable> orders = new LinkedHashSet<>();
    public Set<Sortable> getOrders() {
        return orders;
    }
    public String getOrderStr(){
        return orders.stream().map(x->{
            return x.getSortFields().stream().map(
                    k->{
                        return k.getFullName() + ConstValue.BLANK + x.getOrder();
                    }
            ).collect(Collectors.joining(","));
        }).collect(Collectors.joining(","));
        //return orders.stream().collect(Collectors.joining(","));
    }
    public void addCondition(WrapperQuery fv){
        conditionElements.add(fv);
    }
    public void bindQueryFromRequests(Map<String,String[]> params, TableMetaInfo tableMetaInfo){
        if(null == params){
            return;
        }
        if(null == conditionElements){
            conditionElements = new ArrayList<>();
        }
        Iterator<Map.Entry<String,String[]>> iterator = params.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,String[]> entry = iterator.next();
            if(CommonUtil.isEmpty(entry.getValue())){
                continue;
            }
            String value = entry.getValue()[0];
            if(StringUtil.isEmpty(value)){
                continue;
            }
            String[] keys = entry.getKey().split("\\.");
            if(keys.length < 3){
                continue;
            }
            if("pageInfo".equals(keys[0]) || "orderInfo".equals(keys[0])){
                continue;
            }
            int lastDot = entry.getKey().lastIndexOf(ConstValue.DOT);
            ConditionType conditionType = ConditionType.valueOf(entry.getKey().substring(lastDot + 1).toUpperCase());
            FieldItem left = FieldItem.valueOf(entry.getKey().substring(0,lastDot));
            Collection right;
            switch (conditionType){
                case LIKE:
                    right = Collections.singletonList("%"+value+"%");
                    break;
                case IN:
                case NOTIN:
                    //right = CastUtil.castArray(value,tableMetaInfo.getFieldWithTypes().get(left.getValue().getName()));
                    //break;
                default:
                    //right = Collections.singletonList(CastUtil.cast(value,tableMetaInfo.getFieldWithTypes().get(left.getValue().getName())));
                    right = Collections.singletonList(value);
                    break;
            }
            addCondition(new WrapperQuery(conditionType,left,right));
        }
    }
    public QueryInfo<T> orderBy(Sortable sortable){
        this.orders.add(sortable);
        return this;
    }
}