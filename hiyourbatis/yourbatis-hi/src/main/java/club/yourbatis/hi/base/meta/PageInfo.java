package club.yourbatis.hi.base.meta;

import club.yourbatis.hi.base.Page;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    @Override
    public void setPageSize(int pageSize) {
        if(pageSize > 0 && pageSize <= COMMON_MAX_PAGE_SIZE){
            this.pageSize = pageSize;
        }
    }
    @Override
    public int getPageIndex() {
        return this.pageIndex;
    }

    @Override
    public void setPageIndex(int pageIndex) {
        if(pageIndex > 0 && pageIndex <= COMMON_MAX_PAGE_SIZE)
            this.pageIndex = pageIndex;
    }

    @Override
    public int getRecordCount() {
        return this.recordCount;
    }

    @Override
    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    @Override
    public int getStep() {
        //mysql
        return (getPageIndex() - 1)*getPageSize();
    }
}
