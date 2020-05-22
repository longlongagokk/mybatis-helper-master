package com.mybatishelper.demo.basicmapper;

import com.mybatishelper.core.base.Primary;
import com.mybatishelper.core.base.meta.PageList;
import com.mybatishelper.core.mapper.BasicMapper;
import com.mybatishelper.core.wrapper.ISelectorWrapper;
import com.mybatishelper.core.wrapper.bridge.AbsSqlProvider;
import com.mybatishelper.core.wrapper.query.QuerySqlProvider;
import com.mybatishelper.demo.common.module.QueryInfo;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 防止被自动化装载
 * @param <T>
 * @param <V>
 */
public interface CommonBasicMapper<T,V extends T> extends BasicMapper<T> {
    @SelectProvider(type = QuerySqlProvider.class, method = AbsSqlProvider.selectItemByPrimaryKey)
    V selectItemByPrimaryKeyV(Primary primary);
    @SelectProvider(type = QuerySqlProvider.class, method = AbsSqlProvider.selectOne)
    V selectOneV(ISelectorWrapper wrapper);
    @SelectProvider(type = QuerySqlProvider.class, method = AbsSqlProvider.selectList)
    List<V> selectListV(ISelectorWrapper wrapper);
    @SelectProvider(type = QuerySqlProvider.class, method = AbsSqlProvider.selectPageList)
    PageList<V> selectPageListV(ISelectorWrapper wrapper);

    int getCountPaging(QueryInfo<T> query);
    List<V> getListByBean(QueryInfo<T> query);
}
