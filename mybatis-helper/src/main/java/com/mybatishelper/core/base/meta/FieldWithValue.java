package com.mybatishelper.core.base.meta;

import com.mybatishelper.core.base.FieldValue;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.param.ParamItem;
import com.mybatishelper.core.base.param.ValueItem;

public class FieldWithValue<T> implements FieldValue<T> {
    private FieldItem left;
    private Item<T> right;
    private T value;
    private FieldWithValue(FieldItem left,Item<T> right){
        this.left = left;
        this.right = right;
        this.value = right.getValue();
    }
    public static <T> FieldWithValue valueOf(String fieldWithAlias, Item<T> right){
        return new FieldWithValue(FieldItem.valueOf(fieldWithAlias),right);
    }
    public static <T> FieldWithValue withParamValue(String fieldWithAlias, T value){
        return new FieldWithValue(FieldItem.valueOf(fieldWithAlias), ParamItem.valueOf(value));
    }
    @Deprecated
    public static <T> FieldWithValue withParamValue(Enum em, T value){
        return withParamValue(em.name(),value);
    }
    public static <T> FieldWithValue<T> withOriginalValue(String fieldWithAlias, T value){
        return new FieldWithValue(FieldItem.valueOf(fieldWithAlias), ValueItem.valueOf(value));
    }
    @Override
    public FieldItem getLeft() {
        return this.left;
    }

    @Override
    public Item<T> getRight() {
        return this.right;
    }

    @Override
    public T getValue() {
        return this.value;
    }
}
