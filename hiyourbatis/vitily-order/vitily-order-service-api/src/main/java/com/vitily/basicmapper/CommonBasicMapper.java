package com.vitily.basicmapper;

import club.yourbatis.hi.base.Primary;
import club.yourbatis.hi.base.meta.PageList;
import club.yourbatis.hi.mapper.BasicMapper;
import club.yourbatis.hi.wrapper.ISelectorWrapper;
import club.yourbatis.hi.wrapper.query.QuerySqlProvider;
import com.vitily.common.module.QueryInfo;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 防止被自动化装载
 * @param <T>
 * @param <V>
 */
public interface CommonBasicMapper<T,V extends T> extends BasicMapper<T> {
    @SelectProvider(type = QuerySqlProvider.class, method = "selectItemByPrimaryKey")
    V selectItemByPrimaryKeyV(Primary primary);
    @SelectProvider(type = QuerySqlProvider.class, method = "selectOne")
    V selectOneV(ISelectorWrapper wrapper);
    @SelectProvider(type = QuerySqlProvider.class, method = "selectList")
    List<V> selectListV(ISelectorWrapper wrapper);
    @SelectProvider(type = QuerySqlProvider.class, method = "selectPageList")
    PageList<V> selectPageListV(ISelectorWrapper wrapper);

    int getCountPaging(QueryInfo<T> query);
    List<V> getListByBean(QueryInfo<T> query);
}
