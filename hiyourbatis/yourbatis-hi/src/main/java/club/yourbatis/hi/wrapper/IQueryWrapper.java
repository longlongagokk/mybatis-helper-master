package club.yourbatis.hi.wrapper;
import java.util.function.Consumer;

/**
 * @param <S> 自身
 */
public interface IQueryWrapper<S,C> extends IWrapper {
    C getWhere();
    S where(Consumer<C> consumer);
}
