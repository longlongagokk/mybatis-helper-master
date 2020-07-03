package com.mybatishelper.core.base.param;

import com.mybatishelper.core.base.Field;
import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.field.CompareField;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.enums.ItemType;
import lombok.Getter;

/**
 * 字段参数
 */
@Getter
public class FieldItem implements Item<Field> {
    private Field value;

    private FieldItem(Field field) {
        this.value = field;
    }

    public static Item<Field> valueOf(Field field) {
        return new FieldItem(field);
    }
    public static Item<Field> valueOf(String fullName) {
        return valueOf(CompareField.valueOf(fullName));
    }
    @Deprecated
    public static Item<Field> valueOf(String alias,Enum em) {
        return valueOf(CompareField.valueOf(alias + ConstValue.DOT + em.name(),false));
    }

    @Override
    public String toString() {
        return value.getFullName();
    }
    @Override
    public ItemType getType() {
        return ItemType.FIELD;
    }
}
