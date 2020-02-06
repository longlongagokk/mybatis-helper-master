package club.yourbatis.hi.util;

import club.yourbatis.hi.annotation.Column;
import club.yourbatis.hi.annotation.PrimaryKey;
import club.yourbatis.hi.annotation.Table;
import club.yourbatis.hi.base.Field;
import club.yourbatis.hi.base.field.SimpleField;
import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.cache.TableInfoCache;
import club.yourbatis.hi.mapper.BasicMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
            Class<?> c = parentType.getRawClass();
            if (c == BasicMapper.class) {
                return TableInfoCache.saveTableInfoIntoMapper(mapperClass,getTableInfoFromEntityClass(parentType.getGeneric(0).getRawClass()));
            }
        }
        throw new RuntimeException("can not load anny table info !");
    }

    public static TableMetaInfo getTableInfoFromEntityClass(Class<?> entityClass) {
        TableMetaInfo tableMetaInfo = TableInfoCache.getTableInfoByEntity(entityClass);
        if (tableMetaInfo != null) {
            return tableMetaInfo;
        }
        try {
            Table table = entityClass.getAnnotation(Table.class);
            Map<String, String> fieldColumn = new ConcurrentHashMap<>();
            Map<String, Class<?>> fieldTypes = new ConcurrentHashMap<>();
            Field primaryKey = null;
            Class<?> allClass = entityClass;
            while (allClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
                java.lang.reflect.Field[] fields = allClass.getDeclaredFields();
                for (java.lang.reflect.Field f : fields) {
                    Column column = f.getAnnotation(Column.class);
                    if (column != null) {
                        fieldColumn.put(f.getName(), column.value());
                        fieldTypes.put(f.getName(),f.getType());
                    }
                    PrimaryKey key = f.getAnnotation(PrimaryKey.class);
                    if(key != null && primaryKey == null){
                        // todo review
                        primaryKey = SimpleField.valueOf(column.value());
                    }
                }
                allClass = allClass.getSuperclass();
            }
            String[] columns = new String[fieldColumn.size()];
            Iterator<Map.Entry<String,String>> it = fieldColumn.entrySet().iterator();
            int index = 0;
            while(it.hasNext()){
                columns[index++] = it.next().getValue();
            }
            return TableInfoCache.saveTableInfoIntoEntity(entityClass,new TableMetaInfo(table.value(), primaryKey, columns, fieldColumn,fieldTypes));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        throw new RuntimeException("can not load any table info !please check your entity !");
    }

    public static Field getColumnItemByFieldItem(Map<String, TableMetaInfo> aliasTables, Field fieldItem){
        TableMetaInfo tb = aliasTables.get(fieldItem.getAlias());
        return getColumnItemByFieldItem(tb,fieldItem);
    }
    public static Field getColumnItemByFieldItem(TableMetaInfo tb, Field fieldItem){
        //if tb is null then some alias not in sql,please check sql's alias
        String column = tb.getFieldWithColumns().get(fieldItem.getName());
        Assert.notEmpty(column,"column can't find in " + tb.getTableName() + " with field " + fieldItem.getFullName());
        return SimpleField.valueOf(fieldItem.getAlias(),column);
    }

}
