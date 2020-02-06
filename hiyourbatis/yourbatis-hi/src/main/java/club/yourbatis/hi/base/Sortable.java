package club.yourbatis.hi.base;

import club.yourbatis.hi.enums.Order;

import java.util.Set;

public interface Sortable {
    Set<Field> getSortFields();
    Order getOrder();
}
