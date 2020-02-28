package club.yourbatis.hi.base.meta;

import club.yourbatis.hi.base.Field;
import club.yourbatis.hi.base.Sortable;
import club.yourbatis.hi.base.field.OrderField;
import club.yourbatis.hi.enums.Order;
import club.yourbatis.hi.util.CollectionUtils;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class Sorter implements Sortable {
    private Set<OrderField> fields;
    private Order order;
    private Sorter(Order order, OrderField ...fields){
        if(CollectionUtils.isEmpty(fields)){
            throw new RuntimeException("no sort field");
        }
        this.fields = new LinkedHashSet<>(fields.length<<1);
        Collections.addAll(this.fields, fields);
        this.order = order;
    }
    public static Sorter valueOf(Order order, OrderField ...fields){
        return new Sorter(order,fields);
    }

    @Override
    public Set<OrderField> getSortFields() {
        return fields;
    }

    @Override
    public Order getOrder() {
        return order;
    }
}
