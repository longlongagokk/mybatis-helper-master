package com.vitily.order.api.mapper;

import club.yourbatis.hi.mapper.BasicMapper;
import club.yourbatis.hi.wrapper.IQueryWrapper;
import club.yourbatis.hi.wrapper.ISelectorWrapper;
import club.yourbatis.hi.wrapper.query.QuerySqlProvider;
import com.vitily.common.module.TvPageList;
import com.vitily.order.api.entity.PageList;
import com.vitily.order.api.entity.TrOrder;
import org.apache.ibatis.annotations.*;

public interface TrOrderMapper extends BasicMapper<TrOrder> {
    @SelectProvider(type = QuerySqlProvider.class, method = "selectPageList")
    //@ResultMap("listMap,countMap")
    @Results(
            id="result1",
            value = {
                    @Result(property = "count",javaType = Integer.class,column = "count")
            }
    )
    //@ResultMap("listMap,countMap")
        //@ResultMap("result1")
    PageList<TrOrder> selectPageList(ISelectorWrapper iSelectorWrapper);
}