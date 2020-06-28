package com.mybatishelper.core.annotation;

import java.lang.annotation.*;

/**
 * 列信息
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
    /**
     * 列名
     */
    String value();

    /**
     * 是否主键
     */
    boolean primaryKey() default false;
}
