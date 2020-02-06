package com.vitily.common.mapper;

import com.vitily.common.module.QueryInfo;
import java.util.List;

public interface CommonBasicMapper<T,V>{
    int getCountPaging(QueryInfo<T> query);
    List<V> getListByBean(QueryInfo<T> query);
}
