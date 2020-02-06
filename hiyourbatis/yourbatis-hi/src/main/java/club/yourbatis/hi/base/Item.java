package club.yourbatis.hi.base;

import club.yourbatis.hi.enums.ItemType;

public interface Item<T> {
    T getValue();
    Item<T> withValue(T value);
    ItemType getType();
}
