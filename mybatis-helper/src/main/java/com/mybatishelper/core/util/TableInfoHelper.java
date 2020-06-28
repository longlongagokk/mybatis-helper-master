package com.mybatishelper.core.util;

import com.mybatishelper.core.cache.TableInfoCache;
import com.mybatishelper.core.cache.TableMetaInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Type;

@Slf4j
public abstract class TableInfoHelper {
    /**
     * get from context class，if not exists then set into cache(lazy init)
     *
     * @param context current request param
     * @return table info
     */
    public static TableMetaInfo getTableInfoByProviderContext(ProviderContext context) {
        Class<?> mapperClass = context.getMapperType();
        return getTableInfoFromMapperClass(mapperClass);
    }

    public static TableMetaInfo getTableInfoFromMapperClass(Class<?> mapperClass) {
        TableMetaInfo tableMetaInfo = TableInfoCache.getTableInfoByMapper(mapperClass);
        if (tableMetaInfo != null) {
            return tableMetaInfo;
        }

        for (Type parent : mapperClass.getGenericInterfaces()) {
            ResolvableType parentType = ResolvableType.forType(parent);
//            Class<?> c = parentType.getRawClass();
//            if (c == BasicMapper.class) {
//                return TableInfoCache.saveTableInfoIntoMapper(mapperClass,getTableInfoFromEntityClass(parentType.getGeneric(0).getRawClass()));
//            }
            return TableInfoCache.saveTableInfoIntoMapper(mapperClass,getTableInfoFromEntityClass(parentType.getGeneric(0).getRawClass()));
        }
        throw new RuntimeException("can not load anny table info !");
    }

    public static TableMetaInfo getTableInfoFromEntityClass(Class<?> entityClass) {
        TableMetaInfo tableMetaInfo = TableInfoCache.getTableInfoByEntity(entityClass);
        if (tableMetaInfo != null) {
            return tableMetaInfo;
        }
        return TableInfoCache.saveTableInfoIntoEntity(entityClass,new TableMetaInfo(entityClass));
    }
}
