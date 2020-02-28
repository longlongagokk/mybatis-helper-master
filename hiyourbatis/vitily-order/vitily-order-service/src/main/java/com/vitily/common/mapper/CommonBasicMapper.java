package com.vitily.common.mapper;

import club.yourbatis.hi.base.Primary;
import club.yourbatis.hi.mapper.BasicMapper;
import club.yourbatis.hi.wrapper.ISelectorWrapper;
import club.yourbatis.hi.wrapper.query.QuerySqlProvider;
import com.vitily.common.module.QueryInfo;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface CommonBasicMapper<T,V extends T> extends BasicMapper<T> {
    @SelectProvider(type = QuerySqlProvider.class, method = "selectItemByPrimaryKey")
    V selectItemByPrimaryKeyV(Primary primary);
    @SelectProvider(type = QuerySqlProvider.class, method = "selectOne")
    V selectOneV(ISelectorWrapper wrapper);
    @SelectProvider(type = QuerySqlProvider.class, method = "selectList")
    List<V> selectListV(ISelectorWrapper wrapper);
    int getCountPaging(QueryInfo<T> query);
    List<V> getListByBean(QueryInfo<T> query);
}
