package club.yourbatis.hi.wrapper;

public interface IDeleteWrapper<S,C> extends IQueryWrapper<S,C>{
    S delete(String alias);
}
