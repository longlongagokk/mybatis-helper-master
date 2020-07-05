package com.mybatishelper.demo.common.module;

import com.mybatishelper.demo.common.util.PageInfo;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QueryInfo<T> {
    T entity;
    private PageInfo pageInfo;
}
