package com.mybatishelper.core.base.meta;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageList<T> implements Serializable {
    private int count;
    List<T> list;
}
