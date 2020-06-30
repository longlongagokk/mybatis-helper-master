package com.mybatishelper.demo.common.mapper;

import com.mybatishelper.core.wrapper.IDeleteWrapper;
import com.mybatishelper.core.wrapper.IQueryWrapper;
import com.mybatishelper.core.wrapper.IUpdateWrapper;
import com.mybatishelper.core.wrapper.bridge.AbsSqlProvider;
import com.mybatishelper.core.wrapper.delete.DeleteSqlProvider;
import com.mybatishelper.core.wrapper.insert.InsertSqlProvider;
import com.mybatishelper.core.wrapper.query.QuerySqlProvider;
import com.mybatishelper.core.wrapper.update.UpdateSqlProvider;
import org.apache.ibatis.annotations.*;

/**
 * tools-Sql
 */
public interface StaticBoundMapper {
    @DeleteProvider(type = DeleteSqlProvider.class,method = AbsSqlProvider.delete)
    int delete(IDeleteWrapper var1);

    @InsertProvider(type = InsertSqlProvider.class, method = AbsSqlProvider.insert)
    int insert(Object entity);

    @InsertProvider(type = InsertSqlProvider.class, method = AbsSqlProvider.insertSelective)
    int insertSelective(Object entity);

    @UpdateProvider(type = UpdateSqlProvider.class, method = AbsSqlProvider.updateByPrimary)
    int updateByPrimary(Object entity);

    @UpdateProvider(type = UpdateSqlProvider.class, method = AbsSqlProvider.updateSelectiveByPrimaryKey)
    int updateSelectiveByPrimaryKey(Object entity);

    @SelectProvider(type = QuerySqlProvider.class,method = AbsSqlProvider.selectCount)
    int selectCount(IQueryWrapper wrapper);

    @SelectProvider(type = QuerySqlProvider.class,method = AbsSqlProvider.selectExists)
    boolean selectExists(IQueryWrapper wrapper);

    @UpdateProvider(type = UpdateSqlProvider.class,method = AbsSqlProvider.updateSelectItem)
    int updateSelectItem(IUpdateWrapper wrapper);
}
