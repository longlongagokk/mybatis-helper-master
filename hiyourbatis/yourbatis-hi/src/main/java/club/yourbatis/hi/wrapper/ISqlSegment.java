package club.yourbatis.hi.wrapper;

import club.yourbatis.hi.wrapper.bridge.AbstractQueryWrapper;

public interface ISqlSegment {
    String createSql(AbstractQueryWrapper wrapper);
}
