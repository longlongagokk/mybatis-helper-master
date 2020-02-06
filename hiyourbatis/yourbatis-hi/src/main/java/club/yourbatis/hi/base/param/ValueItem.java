package club.yourbatis.hi.base.param;

import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.enums.ItemType;
import lombok.Getter;

@Getter
public class ValueItem<T> implements Item<T> {
    private T value;
    public ValueItem(T value){
        this.value = value;
    }
    public static <T> ValueItem<T> valueOf(T value){
        return new ValueItem<>(value);
    }

    @Override
    public ValueItem<T> withValue(T value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return value.toString();
    }
    @Override
    public ItemType getType() {
        return ItemType.VALUE;
    }
}
