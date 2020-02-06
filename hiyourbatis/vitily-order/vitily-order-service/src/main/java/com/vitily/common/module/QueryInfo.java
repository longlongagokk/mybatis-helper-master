package com.vitily.common.module;

import club.yourbatis.hi.base.Sortable;
import club.yourbatis.hi.base.meta.PageInfo;
import club.yourbatis.hi.consts.ConstValue;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class QueryInfo<T> {
    T entity;
    private PageInfo pageInfo;
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
    public QueryInfo<T> orderBy(Sortable sortable){
        this.orders.add(sortable);
        return this;
    }
}
