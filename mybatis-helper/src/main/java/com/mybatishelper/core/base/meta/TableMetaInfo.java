package com.mybatishelper.core.base.meta;

import lombok.Getter;

import java.util.Map;

@Getter
public class TableMetaInfo {
    private String tableName;
    /**
     * the primary info
     */
    private String primary;
    private Map<String, String> fieldWithColumns;
    private Map<String, Class<?>> fieldWithTypes;
    public TableMetaInfo(String tableName, String primary
            , Map<String, String> fieldWithColumns
            , Map<String, Class<?>> fieldWithTypes
    ){
        this.tableName = tableName;
        this.primary = primary;
        this.fieldWithColumns = fieldWithColumns;
        this.fieldWithTypes = fieldWithTypes;
    }
}
