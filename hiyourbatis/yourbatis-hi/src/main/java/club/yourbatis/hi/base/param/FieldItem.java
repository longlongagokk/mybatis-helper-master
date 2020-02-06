package club.yourbatis.hi.base.param;

import club.yourbatis.hi.base.Field;
import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.enums.ItemType;
import club.yourbatis.hi.base.field.SimpleField;
import lombok.Getter;

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
        return valueOf(SimpleField.valueOf(fullName));
    }
    public static FieldItem valueOf(String alase,Enum em) {
        return valueOf(SimpleField.valueOf(alase,em));
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
