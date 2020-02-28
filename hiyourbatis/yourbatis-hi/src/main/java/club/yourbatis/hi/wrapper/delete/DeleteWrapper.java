package club.yourbatis.hi.wrapper.delete;

import club.yourbatis.hi.wrapper.IDeleteWrapper;
import club.yourbatis.hi.wrapper.bridge.AbstractConditionWrapper;
import club.yourbatis.hi.wrapper.bridge.AbstractJoinerWrapper;
import club.yourbatis.hi.wrapper.factory.StringConditionWrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DeleteWrapper<C extends AbstractConditionWrapper>
        extends AbstractJoinerWrapper<C, DeleteWrapper<C>>
        implements IDeleteWrapper<DeleteWrapper<C>,C> {
    Set<String> deleteAlias = new HashSet<>(1<<1);
    public DeleteWrapper(C where){
        super(where,new ArrayList<>(AbstractConditionWrapper.DEFAULT_CONDITION_ELEMENTS_SIZE));
    }

    public static IDeleteWrapper<DeleteWrapper<StringConditionWrapper>,StringConditionWrapper> build(){
        return new DeleteWrapper<>(new StringConditionWrapper());
    }
    @Override
    public DeleteWrapper<C> delete(String alias) {
        deleteAlias.add(alias);
        return this;
    }
}
