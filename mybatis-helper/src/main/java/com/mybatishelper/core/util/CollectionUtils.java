package com.mybatishelper.core.util;

import java.util.Collection;

public abstract class CollectionUtils {
    public static boolean isEmpty(Object[] array){
        return array == null || array.length == 0;
    }
    public static boolean isNotEmpty(Object[] array){
        return !(isEmpty(array));
    }
    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }
}
