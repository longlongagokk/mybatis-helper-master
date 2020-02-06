package club.yourbatis.hi.wrapper;

import club.yourbatis.hi.base.Field;
import club.yourbatis.hi.enums.Order;

public interface IOrder<S> {
    S orderBy(Order order, Field... fields);
    S orderBy(Order order, String strings);
}
