package com.mybatishelper.core.base.param;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.enums.ItemType;
import lombok.Getter;

/**
 * 字段参数
 */
@Getter
public class FieldItem<T> implements Item<T> {
    private String alias;
    private String name;
    private T value;
    private FieldItem(String fieldWithAlias) {
        setNameAndAlias(fieldWithAlias);
    }
    private FieldItem(String alias,String name) {
        this.alias = alias;
        this.name = name;
    }

    public static <T> FieldItem<T> valueOf(String fieldWithAlias) {
        return new FieldItem<>(fieldWithAlias);
    }
    public static <T> FieldItem<T> valueOf(String alias,String name) {
        return new FieldItem<>(alias,name);
    }
    private void setNameAndAlias(String nameWithAlias){
        int dotIndex = nameWithAlias.indexOf(ConstValue.DOT);
        if(dotIndex == -1){
            name = nameWithAlias;
        }else{
            alias = nameWithAlias.substring(0,dotIndex);
            name = nameWithAlias.substring(dotIndex + 1);
        }
    }

    @Override
    public T getValue() {
        return value;
    }
    @Override
    public ItemType getType() {
        return ItemType.FIELD;
    }
}
