package com.mybatishelper.core.util;

import java.util.Collection;

public abstract class Assert {
    public static void notEmpty(String str,String message){
        if(str == null || "".equals(str)){
            throw new IllegalArgumentException(message);
        }
    }
    public static void notEmpty(Collection<?> collections, String message){
        org.springframework.util.Assert.notEmpty(collections,message);
    }
    public static void notNull(Object obj,String message){
        org.springframework.util.Assert.notNull(obj,message);
    }
}
