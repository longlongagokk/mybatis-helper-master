package com.mybatishelper.core.util;

import java.util.Collection;

public abstract class Assert {
    public static void notEmpty(String str,String message){
        if(str == null || "".equals(str)){
            throw new IllegalArgumentException(message);
        }
    }
    public static void notEmpty(Collection<?> collection, String message){
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }
    /**
     * Assert that an array contains elements; that is, it must not be
     * {@code null} and must contain at least one element.
     * <pre class="code">Assert.notEmpty(array, "The array must contain elements");</pre>
     * @param array the array to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object array is {@code null} or contains no elements
     */
    public static void notEmpty(Object[] array, String message) {
        if (CollectionUtils.isEmpty(array)) {
            throw new IllegalArgumentException(message);
        }
    }
    public static void notNull(Object object,String message){
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }
}
