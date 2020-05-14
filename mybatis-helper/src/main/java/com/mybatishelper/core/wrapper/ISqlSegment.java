package com.mybatishelper.core.wrapper;

import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;

public interface ISqlSegment {
    String createSql(AbstractQueryWrapper wrapper);
}
