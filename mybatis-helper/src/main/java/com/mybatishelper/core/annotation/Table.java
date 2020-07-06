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

    /**
     * 默认使用 column使用驼峰式的字段转成，如果不使用，则使用字段名转化成column名，
     * 如果字段上有@Column，优先使用@Column操作
     */
    boolean columnToCamelCase() default true;
}
