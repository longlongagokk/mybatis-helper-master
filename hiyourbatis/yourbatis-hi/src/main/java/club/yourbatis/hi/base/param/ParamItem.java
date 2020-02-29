package club.yourbatis.hi.base.param;

import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.enums.ItemType;
import lombok.Getter;

/**
 * 预编译参数
 * @param <T>
 */
@Getter
public class ParamItem<T> implements Item<T> {
    private T value;
    /**
     * param index
     */
    private int index;

    private ParamItem(T value, int index) {
        this.value = value;
        this.index = index;
    }

    public static <T> ParamItem valueOf(T value, int index) {
        return new ParamItem(value, index);
    }
    public static <T> ParamItem valueOf(T value) {
        return valueOf(value,0);
    }

    @Override
    public ParamItem<T> withValue(T value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "#{" + value + "[" + index + "].value}";
    }

    @Override
    public ItemType getType() {
        return ItemType.PARAM;
    }
}
