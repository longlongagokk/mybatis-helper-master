package club.yourbatis.hi.base.meta;

import club.yourbatis.hi.base.FieldValue;
import club.yourbatis.hi.base.param.FieldItem;
import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.base.param.ParamItem;
import club.yourbatis.hi.base.param.ValueItem;

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
