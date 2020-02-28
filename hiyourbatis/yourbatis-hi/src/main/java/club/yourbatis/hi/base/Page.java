package club.yourbatis.hi.base;

public interface Page {
    /**
     * every page has how many size
     * @return page size
     */
    int getPageSize();

    /**
     * current page,1 begin
     * @return current page
     */
    int getPageIndex();
}
