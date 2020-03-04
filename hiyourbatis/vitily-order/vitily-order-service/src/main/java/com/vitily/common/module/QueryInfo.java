package com.vitily.common.module;

import club.yourbatis.hi.base.field.OrderField;
import com.vitily.common.util.PageInfo;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class QueryInfo<T> {
    T entity;
    private PageInfo pageInfo;
    @Getter
    private List<OrderField> orders = new ArrayList<>();
    public String getOrderStr(){
        return orders.stream().map(x->{
            return x.getFullName() + x.getOrder();
        }).collect(Collectors.joining(","));
        //return orders.stream().collect(Collectors.joining(","));
    }
    public QueryInfo<T> orderBy(OrderField sortable){
        this.orders.add(sortable);
        return this;
    }
}
