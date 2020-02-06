package com.vitily.common.module;

import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.enums.ConditionType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;

@Data
@Accessors(chain = true)
public class WrapperQuery<T> {
    private ConditionType type;
    private Item<T> left;
    private Collection<?> right;

    public WrapperQuery(ConditionType type, Item<T> left, Collection<?> right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }
}
