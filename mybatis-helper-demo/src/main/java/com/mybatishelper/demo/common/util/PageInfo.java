package com.mybatishelper.demo.common.util;

import com.mybatishelper.core.base.Page;

public class PageInfo implements Page {
    public static final int COMMON_DEFAULT_PAGE_SIZE = 10;
    public static final int COMMON_MAX_PAGE_SIZE = 50000000;
    /**
     * 每页显示条数
     */
    private int pageSize;
    /**
     * 当前页数 1 起步
     */
    private int pageIndex;
    /**
     * 总纪录
     */
    private int recordCount;

    private PageInfo(int pageIndex, int pageSize) {
        setPageIndex(pageIndex);
        setPageSize(pageSize);
    }
    public PageInfo() {
        this(1,COMMON_DEFAULT_PAGE_SIZE);
    }

    public static PageInfo valueOf(int pageIndex) {
        return valueOf(pageIndex, COMMON_DEFAULT_PAGE_SIZE);
    }

    public static PageInfo valueOf(int pageIndex, int pageSize) {
        return new PageInfo(pageIndex, pageSize);
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        int tmp = COMMON_DEFAULT_PAGE_SIZE;
        if(pageSize > 0 && pageSize <= COMMON_MAX_PAGE_SIZE){
            tmp = pageSize;
        }
        this.pageSize = tmp;
    }
    @Override
    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        int tmp = 1;
        if(pageIndex > 0 && pageIndex <= COMMON_MAX_PAGE_SIZE){
            tmp = pageIndex;
        }
        this.pageIndex = tmp;
    }

    public int getRecordCount() {
        return this.recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getStep() {
        //mysql
        return (getPageIndex() - 1)*getPageSize();
    }
}
