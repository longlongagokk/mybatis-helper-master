package club.yourbatis.hi.wrapper;

import club.yourbatis.hi.base.FieldValue;

/**
 * @param <S> 自身
 */
public interface IUpdateWrapper<S,C> extends IQueryWrapper<S,C>,
        IJoining<S,C>
{
    S set(FieldValue... items);
    S set(String fieldWithAlias, Object value);
    S set(Enum field, Object value);
}
