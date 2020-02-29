package club.yourbatis.hi.wrapper;

import club.yourbatis.hi.base.field.OrderField;
import club.yourbatis.hi.enums.Order;

public interface IOrder<S> {
    S orderBy(OrderField... fields);
    S orderBy(String strings);
}
