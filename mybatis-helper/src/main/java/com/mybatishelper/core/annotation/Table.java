package com.mybatishelper.core.annotation;

import java.lang.annotation.*;

/**
 * 实体类对应表名
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    String value();
}
