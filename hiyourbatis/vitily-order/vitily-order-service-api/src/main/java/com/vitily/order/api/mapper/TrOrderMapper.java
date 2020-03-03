package com.vitily.order.api.mapper;

import club.yourbatis.hi.wrapper.ISelectorWrapper;
import club.yourbatis.hi.wrapper.query.QuerySqlProvider;
import com.vitily.order.api.entity.PageList;
import com.vitily.order.api.entity.TrOrder;
import org.apache.ibatis.annotations.SelectProvider;

public interface TrOrderMapper {
    @SelectProvider(type = QuerySqlProvider.class, method = "selectPageList")
    //@ResultMap("listMap,countMap")
//    @Results(
//            id="result1",
//            value = {
//                    @Result(property = "count",javaType = Integer.class,column = "count")
//            }
//    )
    //@ResultMap("listMap,countMap")
    //@ResultMap("listMap")
    PageList<TrOrder> selectPageList(ISelectorWrapper iSelectorWrapper);
}