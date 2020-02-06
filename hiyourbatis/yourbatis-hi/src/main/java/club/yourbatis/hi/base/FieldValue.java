package club.yourbatis.hi.base;

import club.yourbatis.hi.base.param.FieldItem;

public interface FieldValue<T> {
    FieldItem getLeft();
    Item<T> getRight();
    T getValue();
}
