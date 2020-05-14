package com.mybatishelper.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ContextUtil {
    public static final Field[] getAllColumnFieldOfObject(Class<?> entityClass){
        List<Field> fieldList = new ArrayList<>() ;
        while (entityClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(entityClass.getDeclaredFields()));
            entityClass = entityClass.getSuperclass(); //得到父类,然后赋给自己
        }
        Field[] fields = new Field[fieldList.size()];
        for(int i = 0;i<fieldList.size();++i){
            fields[i] = fieldList.get(i);
        }
        return fields;
    }
}
