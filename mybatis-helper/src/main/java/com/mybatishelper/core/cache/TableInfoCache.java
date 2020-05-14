package com.mybatishelper.core.cache;

import com.mybatishelper.core.base.meta.TableMetaInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class TableInfoCache {
    private static final Map<Class<?>, TableMetaInfo> MAPPER_INFO_CACHE = new ConcurrentHashMap<>();
    /**
     * same to mapper value but key is not same
     */
    private static final Map<Class<?>, TableMetaInfo> ENTITY_INFO_CACHE = new ConcurrentHashMap<>();
    public static TableMetaInfo getTableInfoByMapper(Class<?> mapper){
        return MAPPER_INFO_CACHE.get(mapper);
    }
    public static TableMetaInfo getTableInfoByEntity(Class<?> entity){
        return ENTITY_INFO_CACHE.get(entity);
    }
    public static TableMetaInfo saveTableInfoIntoMapper(Class<?> mapper, TableMetaInfo tableMetaInfo){
        MAPPER_INFO_CACHE.put(mapper, tableMetaInfo);
        return tableMetaInfo;
    }
    public static TableMetaInfo saveTableInfoIntoEntity(Class<?> entity, TableMetaInfo tableMetaInfo){
        MAPPER_INFO_CACHE.put(entity, tableMetaInfo);
        return tableMetaInfo;
    }
}
