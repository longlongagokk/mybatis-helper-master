package com.vitily.order.api.mapper;

import club.yourbatis.hi.base.meta.PageList;
import club.yourbatis.hi.wrapper.ISelectorWrapper;
import club.yourbatis.hi.wrapper.query.QuerySqlProvider;
import com.vitily.order.api.entity.TrOrder;
import org.apache.ibatis.annotations.SelectProvider;

public interface TrOrderMapper {
    @SelectProvider(type = QuerySqlProvider.class, method = "selectPageList")
    PageList<TrOrder> selectPageList(ISelectorWrapper iSelectorWrapper);
}