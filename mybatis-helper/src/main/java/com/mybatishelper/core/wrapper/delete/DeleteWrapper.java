package com.mybatishelper.core.wrapper.delete;

import com.mybatishelper.core.wrapper.IDeleteWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractConditionWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DeleteWrapper<C extends AbstractConditionWrapper>
        extends AbstractQueryWrapper<C, DeleteWrapper<C>>
        implements IDeleteWrapper<DeleteWrapper<C>,C> {
    Set<String> deleteAlias = new HashSet<>(1<<1);
    public DeleteWrapper(C where){
        super(where,new ArrayList<>(AbstractConditionWrapper.DEFAULT_CONDITION_ELEMENTS_SIZE));
    }
    @Override
    public DeleteWrapper<C> delete(String alias) {
        deleteAlias.add(alias);
        return this;
    }
}
