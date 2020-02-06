package club.yourbatis.hi.wrapper;

import club.yourbatis.hi.base.Page;

public interface IPager<S> {
    Page getPage();
    S page(Page page);
}
