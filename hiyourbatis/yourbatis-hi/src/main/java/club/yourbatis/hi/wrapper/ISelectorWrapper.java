package club.yourbatis.hi.wrapper;

import club.yourbatis.hi.base.field.SelectField;

import java.util.Collection;

/**
 * @param <S> 自身
 */
public interface ISelectorWrapper<S,C>
        extends IQueryWrapper<S,C>,
        IJoining<S,C>,
        IPager<S>, IOrder<S>
{
    Collection<SelectField> selects();
    S select0(SelectField... fields);
    S select1(Enum... fields);
    S select(String fields);
    S selectMain(boolean selectMain);
    S lock(boolean lock);
}
