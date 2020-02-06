package club.yourbatis.hi.wrapper;

/**
 * @param <S> 自身
 */
public interface ICountWrapper<S,C> extends IQueryWrapper<S,C>,
        IJoining<S,C>{
}
