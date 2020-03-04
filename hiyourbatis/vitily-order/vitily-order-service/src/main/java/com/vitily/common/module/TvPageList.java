package com.vitily.common.module;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TvPageList<T> implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int recordCount;
    private List<T> list;
}
