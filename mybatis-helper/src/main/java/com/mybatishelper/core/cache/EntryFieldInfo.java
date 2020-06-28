package com.mybatishelper.core.cache;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.lang.reflect.Field;

@Getter
@Accessors(chain = true)
@Builder
public class EntryFieldInfo implements Serializable {
    /**
     * 字段对应列名称
     */
    private String column;
    /**
     * 是否主键
     */
    private boolean primaryKey;
    /**
     * 字段 属性
     */
    private Field field;
}
