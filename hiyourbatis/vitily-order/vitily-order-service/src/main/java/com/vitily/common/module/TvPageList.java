package com.vitily.common.module;

import club.yourbatis.hi.base.meta.PageInfo;

import java.io.Serializable;
import java.util.List;

public class TvPageList<T> implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private PageInfo pageInfo;
    private List<T> list;
    public PageInfo getPageInfo() {
        return pageInfo;
    }
    public TvPageList<T> setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
        return this;
    }
    public List <T> getList() {
        return list;
    }
    public TvPageList<T> setList(List <T> list) {
        this.list = list;
        return this;
    }
}
