package com.mybatishelper.core.annotation;

import java.lang.annotation.*;

/**
 * 跳过序列化为数据库列
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipColumn { }
