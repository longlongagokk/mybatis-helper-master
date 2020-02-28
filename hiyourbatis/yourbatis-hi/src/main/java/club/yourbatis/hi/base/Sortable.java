package club.yourbatis.hi.base;

import club.yourbatis.hi.base.field.OrderField;
import club.yourbatis.hi.enums.Order;

import java.util.Set;

public interface Sortable {
    Set<OrderField> getSortFields();
    Order getOrder();
}
