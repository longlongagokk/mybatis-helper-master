package club.yourbatis.hi.wrapper;

import club.yourbatis.hi.wrapper.bridge.AbstractQueryWrapper;

public interface ILinkItem {
    String createSql(AbstractQueryWrapper wrapper);
}
