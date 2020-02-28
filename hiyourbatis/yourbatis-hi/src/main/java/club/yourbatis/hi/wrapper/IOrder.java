package club.yourbatis.hi.wrapper;

import club.yourbatis.hi.base.field.OrderField;
import club.yourbatis.hi.enums.Order;

public interface IOrder<S> {
    S orderBy(Order order, OrderField... fields);
    S orderBy(Order order, String strings);
}
