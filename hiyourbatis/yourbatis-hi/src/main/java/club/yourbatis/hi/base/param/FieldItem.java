package club.yourbatis.hi.base.param;

import club.yourbatis.hi.base.Field;
import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.base.field.CompareField;
import club.yourbatis.hi.consts.ConstValue;
import club.yourbatis.hi.enums.ItemType;
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

    public static FieldItem valueOf(Field field) {
        return new FieldItem(field);
    }
    public static FieldItem valueOf(String fullName) {
        return valueOf(CompareField.valueOf(fullName));
    }
    public static FieldItem valueOf(String alias,Enum em) {
        return valueOf(CompareField.valueOf(alias + ConstValue.DOT + em.name(),false));
    }

    @Override
    public Item<Field> withValue(Field value) {
        this.value = value;
        return this;
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
