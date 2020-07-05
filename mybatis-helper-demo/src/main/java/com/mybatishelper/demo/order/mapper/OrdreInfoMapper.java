package com.mybatishelper.demo.order.mapper;

import com.mybatishelper.core.wrapper.ISelectorWrapper;
import com.mybatishelper.core.wrapper.bridge.AbsSqlProvider;
import com.mybatishelper.core.wrapper.query.QuerySqlProvider;
import com.mybatishelper.demo.order.module.entity.TbOrderForm;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface OrdreInfoMapper {
    @SelectProvider(type = QuerySqlProvider.class, method = AbsSqlProvider.selectList)
    List<TbOrderForm> selectListV(ISelectorWrapper wrapper);
}
