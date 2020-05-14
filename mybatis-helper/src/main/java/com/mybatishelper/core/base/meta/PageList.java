package com.mybatishelper.core.base.meta;

import lombok.Data;
import java.util.List;

@Data
public class PageList<T>{
    private int count;
    List<T> list;
}
