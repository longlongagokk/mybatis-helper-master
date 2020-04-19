package com.vitily.order.api.mapper;

import club.yourbatis.hi.base.meta.PageList;
import club.yourbatis.hi.wrapper.ISelectorWrapper;
import club.yourbatis.hi.wrapper.bridge.AbsSqlProvider;
import club.yourbatis.hi.wrapper.query.QuerySqlProvider;
import com.vitily.order.api.entity.TrOrder;
import org.apache.ibatis.annotations.SelectProvider;

public interface TrOrderMapper<T> {
    @SelectProvider(type = QuerySqlProvider.class, method = AbsSqlProvider.selectPageList)
    PageList<TrOrder> selectPageList(ISelectorWrapper iSelectorWrapper);

    @SelectProvider(type = QuerySqlProvider.class, method = AbsSqlProvider.selectList)
    PageList<TrOrder> selectPageList0(ISelectorWrapper iSelectorWrapper);

    @SelectProvider(type = QuerySqlProvider.class, method = AbsSqlProvider.selectOne)
    T selectOne(ISelectorWrapper iSelectorWrapper);

}