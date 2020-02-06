package club.yourbatis.hi.base;

public interface Page {
    /**
     * every page have how many size
     * @return page size
     */
    int getPageSize();
    void setPageSize(int pageSize);

    /**
     * current page,1 begin
     * @return current page
     */
    int getPageIndex();
    void setPageIndex(int pageIndex);

    /**
     * got total row count
     * @return total row count
     */
    int getRecordCount();
    void setRecordCount(int recordCount);

    /**
     * get skip rows,like mysql,some sql such:select fields from table limit step,pageSize,it will return (pageIndex-1)*pageSize
     * @return skip rows
     */
    int getStep();
}
