package com.vitily.order.api.entity;

import lombok.Data;

@Data
public class BaseReq<T> {
    private T entity;
}
